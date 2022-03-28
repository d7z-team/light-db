package org.d7z.light.db.jedis

import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import redis.clients.jedis.Protocol
import java.io.Closeable

/**
 * Jedis Pool
 *
 */
interface LightJedisPool : Closeable {
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

    companion object : LightJedisPool {
        private val pool by lazy {
            JedisPool(
                JedisPoolConfig(),
                System.getProperty("redis.host", Protocol.DEFAULT_HOST),
                System.getProperty("redis.port", Protocol.DEFAULT_PORT.toString()).toInt(),
                System.getProperty("redis.timeout", Protocol.DEFAULT_TIMEOUT.toString()).toInt(),
                System.getProperty("redis.password", null),
                System.getProperty("redis.db", Protocol.DEFAULT_DATABASE.toString()).toInt(),
            )
        }
        override val resource: Jedis
            get() = pool.resource

        override fun close() {
            pool.close()
        }
    }
}
