package org.d7z.light.db.modules.cache.api

import kotlin.reflect.KClass

/**
 * 基于 LightDB 的缓存管理器抽象接口
 */
interface ILightCache {

    /**
     * 创建单入参缓存实例
     *
     * @param name String 实例名称
     * @param keyType KClass<K> 实例 key 类型
     * @param valueType KClass<V> 实例 value 类型
     */
    fun <K : Any, V : Any> singleCacheGroup(
        name: String,
        keyType: KClass<K>,
        valueType: KClass<V>,
    ): ISingleCacheContent<K, V>

    /**
     * 创建多入参缓存实例
     *
     * @param name String 实例名称
     * @param valueType KClass<V> 实例 value 类型
     */
    fun <V : Any> multipleCacheGroup(
        name: String,
        valueType: KClass<V>,
    ): IMultipleCacheContent<V>
}
