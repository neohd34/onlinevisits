package me.chat.onlinevisits.exception

class RequiredRequestParamException(param: String) : Throwable() {
    override val message: String = "Query param [$param] is missing."
}