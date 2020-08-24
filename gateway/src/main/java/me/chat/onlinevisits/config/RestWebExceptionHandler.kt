package me.chat.onlinevisits.config

import com.fasterxml.jackson.databind.ObjectMapper
import me.chat.onlinevisits.data.ProcessingResponse
import org.apache.logging.log4j.LogManager
import org.springframework.core.annotation.Order
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.core.io.buffer.DefaultDataBufferFactory
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import reactor.core.publisher.Mono

@Component
@Order(-2)
class RestExceptionHandler(private val objectMapper: ObjectMapper) : WebExceptionHandler {

    companion object {
        private val log = LogManager.getLogger()
    }

    override fun handle(exchange: ServerWebExchange, ex: Throwable): Mono<Void> {

        exchange.response.statusCode = HttpStatus.BAD_REQUEST
        exchange.response.headers.contentType = MediaType.APPLICATION_JSON_UTF8

        log.warn("Unhandled exception.", ex)

        val writeValueAsBytes = objectMapper.writeValueAsBytes(ProcessingResponse(false, ex.message))
        val db = DefaultDataBufferFactory().wrap(writeValueAsBytes)
        return exchange.response.writeWith(Mono.just<DataBuffer>(db))
    }
}