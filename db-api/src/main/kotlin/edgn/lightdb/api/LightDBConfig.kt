package edgn.lightdb.api

import java.util.concurrent.TimeUnit

/**
 * lightDB 实例配置
 */
interface LightDBConfig {
    /**
     * 默认过期时间
     */
    fun defaultExpire(timeout: Long, unit: TimeUnit)
}
