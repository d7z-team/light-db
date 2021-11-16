package edgn.lightdb.jedis

import edgn.lightdb.api.LightDBConfig
import java.util.concurrent.TimeUnit

/**
 * Jedis 相关配置
 */
class JedisLightDBConfig : LightDBConfig {
    override fun defaultExpire(timeout: Long, unit: TimeUnit) {
        TODO("Not yet implemented")
    }
}
