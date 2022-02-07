package edgn.lightdb.modules.cache.internal

import edgn.lightdb.api.structs.map.LightMapGroup
import edgn.lightdb.modules.cache.api.ISingleCacheContent
import java.util.Optional
import kotlin.reflect.KClass

open class SingleCacheContent<K : Any, V : Any>(
    override val group: String,
    private val keyType: KClass<K>,
    private val valueType: KClass<V>,
    private val cacheContainer: LightMapGroup,
) :
    ISingleCacheContent<K, V> {
    override fun save(key: K, value: V): Optional<V> {
        return cacheContainer.getOrCreate(group, keyType, valueType).put(key, value)
    }

    override fun saveIfAbsent(key: K, value: V): V {
        return cacheContainer.getOrCreate(group, keyType, valueType).putIfAbsent(key, value)
    }

    override fun restore(key: K): Boolean {
        return cacheContainer.getOrCreate(group, keyType, valueType).removeKey(key).isPresent
    }

    override fun get(key: K): Optional<V> {
        return cacheContainer.getOrCreate(group, keyType, valueType)[key]
    }

    override fun exists(key: K): Boolean {
        return cacheContainer.getOrCreate(group, keyType, valueType).containsKey(key)
    }

    override fun clear() {
        cacheContainer.getOrCreate(group, keyType, valueType).clear()
    }
}
