package me.chat.onlinevisits.service

import me.chat.common.ServletAttributes
import me.chat.onlinevisits.data.RequestData
import me.chat.onlinevisits.data.Targeting
import me.chat.onlinevisits.geoip.CountryParser
import nl.basjes.parse.useragent.UserAgentAnalyzer
import nl.basjes.parse.useragent.classify.UserAgentClassifier
import org.apache.logging.log4j.LogManager
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

@Component
@Order(1002)
class TargetingProviderFilter(private val countryParser: CountryParser) : WebFilter {

    companion object {
        private val log = LogManager.getLogger()

        private val analyzer = UserAgentAnalyzer
            .newBuilder()
            .hideMatcherLoadStats()
            .withCache(10000)
            .build()
    }

    override fun filter(serverWebExchange: ServerWebExchange, webFilterChain: WebFilterChain): Mono<Void> {

        // todo refactor
        if (!serverWebExchange.request.uri.path.contains("visits"))
            return webFilterChain.filter(serverWebExchange)

        val req = serverWebExchange.attributes[ServletAttributes.RD] as RequestData
        serverWebExchange.attributes[ServletAttributes.RD] = provideTargeting(req)

        return webFilterChain.filter(serverWebExchange)
    }

    fun provideTargeting(req: RequestData): RequestData {

        val country = countryParser.parse(req.ip)
        val mobile = req.ua?.let {
            UserAgentClassifier.isMobile(analyzer.parse(it))
        } ?: false

        val targeting = Targeting(
            country = country,
            mobile = mobile
        )

        return req.copy(targeting = targeting)
    }
}