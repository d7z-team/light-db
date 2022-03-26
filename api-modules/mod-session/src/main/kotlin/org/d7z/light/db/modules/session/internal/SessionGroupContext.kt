package org.d7z.light.db.modules.session.internal

import org.d7z.light.db.modules.session.LightSession
import org.d7z.light.db.modules.session.api.ISessionContext
import org.d7z.light.db.modules.session.api.ISessionGroupContext
import java.util.Optional

class SessionGroupContext(
    private val lightSession: LightSession,
    private val groupName: String,
    override val name: String,
) : ISessionGroupContext {
    override var survivalTime: Long = lightSession.globalTtl
    val mapContext = lightSession.lightDB.withMap(groupName)

    override fun newSession(): ISessionContext {
        var key = lightSession.sessionIDGenerate.generate(name)
        while (mapContext.exists(key)) {
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
        return mapContext.drop(session)
    }
}
