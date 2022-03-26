package org.d7z.light.db.memory.structs.map

import org.d7z.light.db.api.struct.LightMap
import org.d7z.light.db.api.struct.MapContext
import org.d7z.light.db.memory.utils.DestroyContainer
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

class MemMapContext : MapContext {

    val container = DestroyContainer<MapDataContainer<Any, Any>>()

    @Suppress("UNCHECKED_CAST")
    fun <K : Any, V : Any> getContent(
        key: String,
        keyType: KClass<K>,
        valueType: KClass<V>,
    ): Optional<MapDataContainer<K, V>> {
        return container.getContainer(key).filter {
            it.keyType.isSubclassOf(keyType)
            it.valueType.isSubclassOf(valueType)
        } as Optional<MapDataContainer<K, V>>
    }

    override fun <K : Any, V : Any> get(
        key: String,
        keyType: KClass<K>,
        valueType: KClass<V>,
    ): Optional<out LightMap<K, V>> {
        return getContent(key, keyType, valueType).map { MemLightMap(key, keyType, valueType, this) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <K : Any, V : Any> getOrCreate(
        key: String,
        keyType: KClass<K>,
        valueType: KClass<V>,
        create: () -> Pair<K, V>,
    ): LightMap<K, V> {
        return container.getOrCreate(key) {
            val map = ConcurrentHashMap<K, V>()
            val pair = create()
            map.put(pair.first, pair.second)
            MapDataContainer(keyType, valueType, map) as MapDataContainer<Any, Any>
        }.let {
            if (it.keyType.isSubclassOf(keyType).not() || it.valueType.isSubclassOf(valueType).not()) {
                throw ClassCastException(
                    "${it.keyType} not SubclassOf $keyType or " +
                        "${it.valueType} not SubclassOf $valueType ."
                )
            }
            MemLightMap(key, keyType, valueType, this)
        }
    }

    override fun exists(key: String): Boolean {
        return container.exists(key)
    }

    override fun getTimeout(key: String): Long {
        return container.getTimeout(key)
    }

    override fun setTimeout(key: String, second: Long): Boolean {
        return container.setTimeout(key, second)
    }
}
