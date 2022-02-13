package org.d7z.light.db.modules.session.internal

import org.d7z.light.db.api.structs.map.LightMap
import org.d7z.light.db.modules.session.LightSession
import org.d7z.light.db.modules.session.api.ISessionContext
import org.d7z.light.db.modules.session.api.SessionException
import java.time.LocalDateTime
import java.util.Optional
import kotlin.reflect.KClass

class SessionContext(
    override val sessionId: String,
    private val sessionGroup: SessionGroupContext,
    private val lightSession: LightSession,
) : ISessionContext {
    private val map: LightMap<String, String>
        get() = lightSession.lightDB.withMap(sessionGroup.groupName).get(sessionId, String::class, String::class)
            .orElseThrow { throw SessionException("Session: $sessionId 不存在.") }

    private val notCheckMap: LightMap<String, String>
        get() = lightSession.lightDB.withMap(sessionGroup.groupName)
            .getOrCreate(sessionId, String::class, String::class)

    override var survivalTime: Long
        get() = lightSession.expireHook.ttl(map)
        set(value) {
            lightSession.expireHook.ttl(map, value)
        }

    fun init() {
        notCheckMap.putIfAbsent("createTime", lightSession.dataCovert.format(LocalDateTime.now()))
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

    override fun clear() {
        map.clear()
        init()
    }
}
