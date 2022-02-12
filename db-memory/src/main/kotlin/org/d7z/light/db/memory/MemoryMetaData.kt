package org.d7z.light.db.memory

import org.d7z.light.db.api.structs.MetaData
import java.util.concurrent.TimeUnit

/**
 *  Memory LightDB 下数据配置
 */
interface MemoryMetaData : MetaData {
    /**
     * 剩余过期时间 (秒)
     *
     * 此数据分为如下几种情况
     *
     * - 大于 0 : 表示剩余过期时间
     * - 等于 0 : 表示已经过期或者即将过期
     * - 等于 -1: 表示此数据无过期时间
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
