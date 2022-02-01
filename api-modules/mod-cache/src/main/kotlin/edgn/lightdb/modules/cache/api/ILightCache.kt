package edgn.lightdb.modules.cache.api

import kotlin.reflect.KClass

/**
 * 缓存管理器
 */
interface ILightCache {

    /**
     * 创建单缓存实例
     *
     */
    fun <K : Any, V : Any> singleCacheGroup(
        name: String,
        keyType: KClass<K>,
        valueType: KClass<V>
    ): ISingleCacheContent<K, V>

    fun <V : Any> multipleCacheGroup(
        name: String,
        valueType: KClass<V>
    ): IMultipleCacheContent<V>
}
