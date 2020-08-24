package me.chat.onlinevisits.data

import me.chat.common.General
import java.util.UUID

data class RequestData(
    val sessionId: UUID,
    val ip: String?,
    val ua: String?,
    val targeting: Targeting
)

data class Targeting(val country: String, val mobile: Boolean) {
    companion object {
        val UNKNOWN = Targeting(General.UNKNOWN, false)
    }
}