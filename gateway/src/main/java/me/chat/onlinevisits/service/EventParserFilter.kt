package me.chat.onlinevisits.service

import me.chat.common.Headers
import me.chat.common.Query
import me.chat.common.ServletAttributes
import me.chat.common.util.Ip
import me.chat.onlinevisits.data.RequestData
import me.chat.onlinevisits.data.Targeting
import me.chat.onlinevisits.exception.RequiredRequestParamException
import org.apache.logging.log4j.LogManager
import org.springframework.core.annotation.Order
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono
import java.util.UUID

@Order(1001)
@Component
class EventParserFilter : WebFilter {

    companion object {
        private val log = LogManager.getLogger()
    }

    override fun filter(serverWebExchange: ServerWebExchange, webFilterChain: WebFilterChain): Mono<Void> {

        log.debug("Path: ${serverWebExchange.request.uri}")

        // todo refactor
        if (!serverWebExchange.request.uri.path.contains("visits"))
            return webFilterChain.filter(serverWebExchange)

        serverWebExchange.attributes[ServletAttributes.RD] = getRequestData(serverWebExchange.request)
        return webFilterChain.filter(serverWebExchange)
    }

    fun getRequestData(req: ServerHttpRequest): RequestData {


        val sessionId = req.queryParams[Query.SESSION]?.get(0)
            ?.let { UUID.fromString(it) } ?: throw RequiredRequestParamException(Query.SESSION)

        val ip = req.queryParams[Query.IP]?.get(0)?.takeIf { it.isNotBlank() }
            ?: Ip.fromHeaders(
                req.headers[Headers.X_FORWARDED_FOR]?.get(0),
                req.headers[Headers.CF_CONNECTION_IP]?.get(0),
                req.headers[Headers.X_REAL_IP]?.get(0)
            ) //todo add an IP verifier lib

        val ua = req.headers[Headers.UA]?.get(0)?.take(256)

        return RequestData(
            sessionId = sessionId,
            ip = ip,
            ua = ua,
            targeting = Targeting.UNKNOWN
        )
    }
}