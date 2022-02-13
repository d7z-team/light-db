package org.d7z.light.db.modules.session.api

import org.d7z.light.db.api.structs.map.LightMap

interface IExpireHook {
    /**
     * 获取 TTL 时间
     */
    fun ttl(lightMap: LightMap<String, String>): Long

    /**
     * 设置 TTL 时间
     */
    fun ttl(lightMap: LightMap<String, String>, ttl: Long)
}
