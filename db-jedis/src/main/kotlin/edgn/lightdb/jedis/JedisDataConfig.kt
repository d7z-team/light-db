package edgn.lightdb.jedis

import edgn.lightdb.api.LightDBConfig
import edgn.lightdb.api.tables.DataConfig
import redis.clients.jedis.Jedis
import redis.clients.jedis.util.Pool
import java.util.concurrent.TimeUnit

/**
 * Jedis 相关配置
 */
class JedisDataConfig
@JvmOverloads
constructor(
    private val jedisPool: Pool<Jedis> = DefaultConfig.defaultJedisPool()
) : DataConfig {

    override fun defaultExpire(timeout: Long, unit: TimeUnit) {
        TODO("Not yet implemented")
    }

    fun clone(): LightDBConfig {
        TODO()
    }
}
