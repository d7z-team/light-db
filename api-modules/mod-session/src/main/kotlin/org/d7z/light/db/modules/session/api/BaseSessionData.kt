package org.d7z.light.db.modules.session.api

import java.time.LocalDateTime

/**
 * Session 数据绑定抽象
 *
 * @property sessionContext ISessionContext session 上下文
 */
abstract class BaseSessionData(private val sessionContext: ISessionContext) {
    /**
     * 创建时间
     */
    val createTime: LocalDateTime by sessionContext::createTime

    /**
     * 修改时间
     */
    val updateTime: LocalDateTime by sessionContext::updateTime
}
