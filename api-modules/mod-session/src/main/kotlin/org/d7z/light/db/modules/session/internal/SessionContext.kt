package org.d7z.light.db.modules.session.internal

import org.d7z.light.db.api.struct.LightMap
import org.d7z.light.db.modules.session.LightSession
import org.d7z.light.db.modules.session.api.ISessionContext
import org.d7z.light.db.modules.session.api.SessionException
import java.time.LocalDateTime
import java.util.Optional
import kotlin.reflect.KClass

class SessionContext(
    override val sessionToken: String,
    private val sessionGroup: SessionGroupContext,
    private val lightSession: LightSession,
) : ISessionContext {
    private val map: LightMap<String, String>
        get() = sessionGroup.mapContext.get(sessionToken, String::class, String::class)
            .orElseThrow { throw SessionException("Session: $sessionToken 不存在.") }

    override var survivalTime: Long
        get() = sessionGroup.mapContext.getTimeout(sessionToken)
        set(value) {
            sessionGroup.mapContext.setTimeout(sessionToken, value)
        }

    fun init() {
        // 数据不存在 ，初始化
        sessionGroup.mapContext.getOrCreate(sessionToken, String::class, String::class) {
            Pair("createTime", lightSession.dataCovert.format(LocalDateTime.now()))
        }
        updateTime = LocalDateTime.now()
        survivalTime = sessionGroup.survivalTime
    }

    override var createTime: LocalDateTime by this
    override var updateTime: LocalDateTime by this

    override fun <T : Any> getConfig(name: String, type: KClass<T>): Optional<T> {
        return map[name].map { lightSession.dataCovert.reduce(it, type) }
    }

    override fun <T : Any> putConfig(name: String, type: KClass<T>, data: T) {
        if (name != "updateTime") {
            refresh() // 防止无限递归
        }
        map.put(name, lightSession.dataCovert.format(data, type))
    }

    override fun refresh() {
        updateTime = LocalDateTime.now()
        survivalTime = sessionGroup.survivalTime
    }
}
