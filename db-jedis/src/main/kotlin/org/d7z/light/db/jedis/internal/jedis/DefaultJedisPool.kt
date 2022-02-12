package org.d7z.light.db.jedis.internal.jedis

import org.d7z.light.db.jedis.options.JedisPool
import redis.clients.jedis.Jedis

class DefaultJedisPool : JedisPool {
    private val nativePool by lazy {
        DefaultConfig.defaultJedisPool()
    }
    override val resource: Jedis
        get() = nativePool.resource

    override fun close() {
        nativePool.close()
    }
}
