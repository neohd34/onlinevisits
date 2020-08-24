package me.chat.onlinevisits

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class OnlineVisitsApplication

fun main(args: Array<String>) {
    runApplication<OnlineVisitsApplication>(*args)
}
