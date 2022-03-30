package org.d7z.light.db.modules.session

import org.d7z.light.db.api.LightDB
import org.d7z.light.db.modules.session.api.ILightSession
import org.d7z.light.db.modules.session.api.ISessionContext
import org.d7z.light.db.modules.session.api.ISessionGroupContext
import org.d7z.light.db.modules.session.api.ISessionIDGenerate
import org.d7z.light.db.modules.session.api.SessionException
import org.d7z.light.db.modules.session.internal.SessionGroupContext
import org.d7z.light.db.modules.session.internal.SimpleSessionIdGenerate
import org.d7z.objects.format.api.IDataCovert
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap

class LightSession private constructor(
    val lightDB: LightDB,
    private val namespace: String,
    val dataCovert: IDataCovert,
    val globalTtl: Long,
    val sessionIDGenerate: ISessionIDGenerate,
) : ILightSession {
    private val map = ConcurrentHashMap<String, ISessionGroupContext>()
    override fun findSessionGroupContext(name: String): ISessionGroupContext {
        return map.getOrPut(name) {
            SessionGroupContext(this, "$namespace:$name", name)
        }
    }

    override fun findGroupContextById(sessionId: String): Optional<ISessionContext> {
        val name = sessionIDGenerate.getName(sessionId, map.keys)
            .orElseThrow { throw SessionException("未找到 Session Group :$sessionId") }
        return findSessionGroupContext(name)
            .querySession(sessionId)
    }

    class Builder(override val container: LightDB = LightDB) :
        ILightSession.Builder {
        override var namespace: String = "session"
        override var dataCovert: IDataCovert = IDataCovert
        override var ttl: Long = 60 * 60
        override var sessionIDGenerate: ISessionIDGenerate = SimpleSessionIdGenerate()

        override fun build(): ILightSession {
            return LightSession(container, namespace, dataCovert, ttl, sessionIDGenerate)
        }
    }
}
