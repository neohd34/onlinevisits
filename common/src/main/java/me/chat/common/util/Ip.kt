package me.chat.common.util

import me.chat.common.General.UNKNOWN

object Ip {

    /**
     * Get IP from various sources or return UNKNOWN
     */
    fun fromHeaders(X_FORWARDED_FOR: String?, CF_CONNECTION_IP: String?, X_REAL_IP: String?): String {
        return X_FORWARDED_FOR?.let { h -> h.split(",")[0].takeIf { it.isNotBlank() } }
            ?: CF_CONNECTION_IP?.takeIf { it.isNotBlank() }
            ?: X_REAL_IP?.takeIf { it.isNotBlank() }
            ?: UNKNOWN
    }
}