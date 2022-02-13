package org.d7z.light.db.modules.session.api

import java.util.Optional
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * Session Group 上下文
 */
interface ISessionGroupContext {

    /**
     * group 名称
     */
    val name: String

    /**
     * 配置会话存活时间 (秒)
     *
     * -1 为永不过期
     *
     * 最小单位为 10
     */
    var survivalTime: Long

    /**
     * 创建新的 Session
     * @return ISessionContext 新的 Session 上下文
     */
    fun newSession(): ISessionContext

    /**
     * 根据session id 获取 Session 上下文
     *
     * @param session String session id
     * @return Optional<ISessionContext>
     */
    fun querySession(session: String): Optional<ISessionContext>

    /**
     *
     *  根据 Session ID 获取 Session Data 实例
     *
     * @param session String session ID
     * @param dataType KClass<D> Session Data 类型
     * @param createFunc Function1<ISessionContext, D>  ISessionContext 转 SessionData 函数
     * @return Optional<D> SessionData 实例
     */
    fun <D : BaseSessionData> querySessionData(
        session: String,
        dataType: KClass<D>,
        createFunc: (ISessionContext) -> D = {
            dataType.primaryConstructor!!.call(it)
        },
    ): Optional<D> {
        return querySession(session).map(createFunc)
    }

    /**
     * 根据 Session id 销毁 Session
     *
     *
     * @param session String Session id
     * @return Boolean 如果已存在并已销毁则返回 true
     */
    fun destroy(session: String): Boolean
}
