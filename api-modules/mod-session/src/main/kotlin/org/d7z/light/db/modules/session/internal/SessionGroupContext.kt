package org.d7z.light.db.modules.session.internal

import org.d7z.light.db.modules.session.LightSession
import org.d7z.light.db.modules.session.api.ISessionContext
import org.d7z.light.db.modules.session.api.ISessionGroupContext
import java.util.Optional

class SessionGroupContext(
    private val lightSession: LightSession,
    val groupName: String,
    override val name: String,
) : ISessionGroupContext {
    override var survivalTime: Long = lightSession.globalTtl

    override fun newSession(): ISessionContext {
        val map = lightSession.lightDB.withMap(groupName)
        var key = lightSession.sessionIDGenerate.generate(name)
        while (map.exists(key)) {
            key = lightSession.sessionIDGenerate.generate(name)
        }
        return getOrCreateSession(key)
    }

    private fun getOrCreateSession(session: String): ISessionContext {
        val sessionContext = SessionContext(
            session,
            this,
            lightSession
        )
        sessionContext.init()
        return sessionContext
    }

    override fun querySession(session: String): Optional<ISessionContext> {
        return lightSession.lightDB.withMap(groupName).get(session, String::class, String::class)
            .map { getOrCreateSession(session) }
    }

    override fun destroy(session: String): Boolean {
        return lightSession.lightDB.withMap(groupName).drop(session)
    }
}
