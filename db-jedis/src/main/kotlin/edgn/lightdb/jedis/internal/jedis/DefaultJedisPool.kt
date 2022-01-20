package edgn.lightdb.jedis.internal.jedis

import edgn.lightdb.jedis.options.JedisPool
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
