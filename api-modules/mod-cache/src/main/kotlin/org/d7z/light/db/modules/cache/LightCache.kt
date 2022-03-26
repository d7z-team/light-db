package org.d7z.light.db.modules.cache

import org.d7z.light.db.api.LightDB
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
 */
class LightCache(
    private val lightDB: LightDB,
    private val namespace: String,
) : ILightCache {
    override fun <K : Any, V : Any> singleCacheGroup(
        name: String,
        keyType: KClass<K>,
        valueType: KClass<V>,
    ): ISingleCacheContent<K, V> {
        return SingleCacheContent(name, keyType, valueType, lightDB.withMap(namespace))
    }

    override fun <V : Any> multipleCacheGroup(name: String, valueType: KClass<V>): IMultipleCacheContent<V> {
        return MultipleCacheContent(name, valueType, lightDB.withMap(namespace))
    }
}
