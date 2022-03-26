package org.d7z.light.db.modules.session.api

import org.d7z.light.db.api.LightDB
import org.d7z.objects.format.api.IDataCovert
import java.util.Optional

/**
 * Session 会话管理
 */
interface ILightSession {
    /**
     * 获取名称为`name` 的 SessionGroupContext
     *
     * 如果不存在名为 `name` 的 SessionGroupContext 则创建新的的 SessionGroupContext
     *
     * @param name String 会话组名称
     * @return SessionGroupContext<T> 会话组上下文
     */
    fun findSessionGroupContext(name: String = "default"): ISessionGroupContext

    /**
     * 根据 Session ID 获取 Session Context
     *
     * 注意：Session ID 包含的 name 必须已在当前实例创建
     *
     * @param sessionId String 获取 session id
     */
    fun findGroupContextById(sessionId: String): Optional<ISessionContext>

    /**
     * Session 创造着
     * @property container LightMapGroup
     */
    interface Builder {
        val container: LightDB

        /**
         * 配置类型转换器
         */
        var dataCovert: IDataCovert

        /**
         * cache名称头部
         */
        var namespace: String

        /**
         * Session ID 生成规则
         */
        var sessionIDGenerate: ISessionIDGenerate

        /**
         * 配置全局过期时间 （秒）
         */
        var ttl: Long

        fun build(): ILightSession
    }
}
