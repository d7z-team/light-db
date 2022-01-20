package edgn.lightdb.jedis.options

import edgn.lightdb.api.structs.MetaData
import java.util.concurrent.TimeUnit

/**
 * jedis 相关配置
 */
interface JedisMetaData : MetaData {
    /**
     * 剩余过期时间
     *
     */
    val ttl: Long

    /**
     * 配置数据过期时间
     */
    fun expired(ttl: Long, unit: TimeUnit)

    /**
     * 清除过期时间
     */
    fun clearExpire()
}
