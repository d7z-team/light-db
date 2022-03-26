package org.d7z.light.db.modules.cache.internal

import org.d7z.light.db.api.struct.MapContext
import org.d7z.light.db.modules.cache.api.ISingleCacheContent
import java.util.Optional
import kotlin.reflect.KClass

open class SingleCacheContent<K : Any, V : Any>(
    override val group: String,
    private val keyType: KClass<K>,
    private val valueType: KClass<V>,
    private val cacheContainer: MapContext,
) :
    ISingleCacheContent<K, V> {
    override fun save(key: K, value: V): Optional<V> {
        return cacheContainer.getOrCreate(group, keyType, valueType) {
            Pair(key, value)
        }.put(key, value)
    }

    override fun saveIfAbsent(key: K, value: V): V {
        return cacheContainer.getOrCreate(group, keyType, valueType) {
            Pair(key, value)
        }.putIfAbsent(key, value)
    }

    override fun remove(key: K): Boolean {
        return cacheContainer.get(group, keyType, valueType)
            .map { it.removeKey(key).isPresent }.orElse(false)
    }

    override fun get(key: K): Optional<V> {
        return cacheContainer.get(group, keyType, valueType).map { it[key] }.orElse(Optional.empty())
    }

    override fun exists(key: K): Boolean {
        return cacheContainer.get(group, keyType, valueType)
            .map { it.containsKey(key) }.orElse(false)
    }
}
