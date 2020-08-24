package me.chat.onlinevisits.web

import me.chat.common.ServletAttributes
import me.chat.onlinevisits.data.ProcessingResponse
import me.chat.onlinevisits.data.RequestData
import me.chat.onlinevisits.service.VisitService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ServerWebExchange

@RestController
class VisitController(private val visitService: VisitService) {

    private val ok = ResponseEntity.ok().body(ProcessingResponse(true, null))

    @GetMapping("/visits")
    fun visit(req: ServerWebExchange): ResponseEntity<ProcessingResponse> {
        val reqData = req.attributes[ServletAttributes.RD] as RequestData
        visitService.processVisit(reqData)
        return ok
    }
}