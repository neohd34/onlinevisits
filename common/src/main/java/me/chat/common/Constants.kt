package me.chat.common

object General {
    const val UNKNOWN = "unknown"
    const val CH_NULL = 0L
    const val CH_STR_NULL = ""
    const val CH_ENUM_NULL = -100
    const val FRACTION = 10000
}

object ServletAttributes {
    const val RD = "rd"
}

object Headers {
    const val F2A = "X-Verify"
    const val X_REAL_IP = "X-Real-IP"
    const val CF_CONNECTION_IP = "CF-Connecting-IP"
    const val X_FORWARDED_FOR = "X-Forwarded-For"
    const val UA = "User-Agent"
    const val HOST = "Host"
    const val API_KEY = "Api-Key"
    const val SIGN = "Sign"
    const val CONTENT_TYPE = "Content-Type"
    const val NONCE = "Nonce"
}

object Query {
    const val SESSION = "session"
    const val IP = "ip"
}