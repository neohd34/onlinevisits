package me.chat.common.data.exception

class CacheMissException(key: Any?, cache: String) : Throwable() {
    override val message: String = "There is no key [${key}] in the [${cache}] cache or the cache isn't ready yet."
}