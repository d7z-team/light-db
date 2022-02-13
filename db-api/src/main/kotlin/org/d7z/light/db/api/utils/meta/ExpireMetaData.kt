package org.d7z.light.db.api.utils.meta

import org.d7z.light.db.api.structs.MetaData
import java.util.concurrent.TimeUnit

/**
 *  过期时间配置元数据
 */
interface ExpireMetaData : MetaData {
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
