package me.chat.onlinevisits.web

import me.chat.onlinevisits.data.OnlineVisitors
import me.chat.onlinevisits.service.VisitService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AdminController(private val visitService: VisitService) {

    @GetMapping("/visitors")
    fun visitors(): OnlineVisitors {
        return visitService.findOnlineVisitors()
    }
}