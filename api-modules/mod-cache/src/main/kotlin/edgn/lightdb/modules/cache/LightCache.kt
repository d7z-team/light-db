package edgn.lightdb.modules.cache

import edgn.lightdb.api.structs.map.LightMapGroup
import edgn.lightdb.api.utils.LightDBLoader
import edgn.lightdb.modules.cache.api.ILightCache
import edgn.lightdb.modules.cache.api.IMultipleCacheContent
import edgn.lightdb.modules.cache.api.ISingleCacheContent
import kotlin.reflect.KClass

/**
 *
 * LightDB 模块 - Cache 实现
 *
 * 以 LightDB 下 Map 作内核
 *
 * @property mapGroup LightMapGroup
 * @property config LightCacheConfig
 * @constructor
 */
class LightCache(
    private val mapGroup: LightMapGroup = LightDBLoader.load().withMap("cache"),
    private val config: LightCacheConfig = LightCacheConfig.Builder().build()
) : ILightCache {
    override fun <K : Any, V : Any> singleCacheGroup(
        name: String,
        keyType: KClass<K>,
        valueType: KClass<V>
    ): ISingleCacheContent<K, V> {

        TODO()
    }

    override fun <V : Any> multipleCacheGroup(name: String, valueType: KClass<V>): IMultipleCacheContent<V> {
        TODO("Not yet implemented")
    }
}
