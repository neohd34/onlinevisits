package me.chat.common.data.cache

import java.io.Serializable
import java.util.UUID

data class CachedVisitor(
    val sessionId: UUID,
    val lastSeen: Long
) : Serializable