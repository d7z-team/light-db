package org.d7z.light.db.modules.cache

import org.d7z.light.db.api.structs.map.LightMapGroup
import org.d7z.light.db.api.utils.LightDBLoader
import org.d7z.light.db.modules.cache.api.ILightCache
import org.d7z.light.db.modules.cache.api.IMultipleCacheContent
import org.d7z.light.db.modules.cache.api.ISingleCacheContent
import org.d7z.light.db.modules.cache.internal.MultipleCacheContent
import org.d7z.light.db.modules.cache.internal.SingleCacheContent
import kotlin.reflect.KClass

/**
 *
 * LightDB 模块 - Cache 实现
 *
 * 以 LightDB 下 Map 作内核
 *
 * @property mapGroup LightMapGroup
 * @constructor
 */
class LightCache(
    private val mapGroup: LightMapGroup = LightDBLoader.load().withMap("cache"),
) : ILightCache {
    override fun <K : Any, V : Any> singleCacheGroup(
        name: String,
        keyType: KClass<K>,
        valueType: KClass<V>,
    ): ISingleCacheContent<K, V> {
        return SingleCacheContent(name, keyType, valueType, mapGroup)
    }

    override fun <V : Any> multipleCacheGroup(name: String, valueType: KClass<V>): IMultipleCacheContent<V> {
        return MultipleCacheContent(name, valueType, mapGroup)
    }
}
