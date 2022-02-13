package org.d7z.light.db.modules.session.api

import java.util.Optional

/**
 * Session id 生成器
 */
interface ISessionIDGenerate {
    /**
     * 根据名称生成特定 SESSION ID
     */
    fun generate(name: String): String

    /**
     * 根据Session ID 获取 Session 名称
     *
     * @param sessionId String Session ID
     * @param sessionNameSet Set<String>  已知的 Session ID
     * @return Optional<String> Session 组名称
     */
    fun getName(sessionId: String, sessionNameSet: Set<String>): Optional<String>
}
