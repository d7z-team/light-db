package edgn.lightdb.api.support.config

import java.util.concurrent.TimeUnit

/**
 * 带过期时间的附加配置
 */
interface ExpiredGroupConfig : DataGroupConfig {
    /**
     * 根据传入单位获取对应过期时间
     * 注意：如果实例已过期则返回0 ，如果永不过期则返回 -1
     */
    fun expired(unit: TimeUnit): Long

    /**
     * 配置过期时间，如果传入数值小于0 则立即过期
     * 注意：如果实例已过期则配置无效且抛出异常
     */
    fun expired(date: Long, unit: TimeUnit)

    /**
     * 去除过期时间
     */
    fun clearExpired()
}
