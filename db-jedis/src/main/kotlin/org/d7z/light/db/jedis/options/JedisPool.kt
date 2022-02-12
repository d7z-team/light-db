package org.d7z.light.db.jedis.options

import redis.clients.jedis.Jedis
import java.io.Closeable

/**
 * Jedis Pool
 *
 */
interface JedisPool : Closeable {
    /**
     * 获取一个 jedis 实例
     */
    val resource: Jedis

    /**
     * 新建一个自动回收的会话
     */
    fun <R : Any> session(block: (Jedis) -> R): R {
        val redisSession = this.resource
        try {
            val res = block(redisSession)
            redisSession.close()
            return res
        } catch (e: Exception) {
            redisSession.close()
            throw e
        }
    }
}
