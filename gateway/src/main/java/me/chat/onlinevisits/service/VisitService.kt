package me.chat.onlinevisits.service

import com.hazelcast.core.IMap
import me.chat.common.data.cache.CachedVisitor
import me.chat.onlinevisits.data.OnlineVisitors
import me.chat.onlinevisits.data.RequestData
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.UUID

@Service
class VisitService(val visitors: IMap<UUID, CachedVisitor>) {

    companion object {
        private val log = LogManager.getLogger()
    }

    /**
     * Get all visitors with last recorded visit between `from` and `to`
     */
    fun findOnlineVisitors(): OnlineVisitors {

        val from = LocalDateTime.now().minusMinutes(2)
        val to = from.plusMinutes(1)

        // could be improved by using Criteria API
        val visitorCount = visitors.values
            .filter { it.lastSeen >= from.toEpochSecond(ZoneOffset.UTC) && it.lastSeen < to.toEpochSecond(ZoneOffset.UTC) }
            .count()

        return OnlineVisitors(visitorCount, visitors.size)
    }


    /**
     * Record the current visit: create new visitor entry if it doesn't exist, update otherwise
     */
    fun processVisit(req: RequestData) {

        val now = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
        val session = req.sessionId

        val visitor = visitors[session]?.copy(lastSeen = now) ?: CachedVisitor(session, now)
        visitors[session] = visitor
    }

}