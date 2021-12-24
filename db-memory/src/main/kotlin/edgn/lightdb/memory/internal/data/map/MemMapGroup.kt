package edgn.lightdb.memory.internal.data.map

import edgn.lightdb.api.structs.map.LightMapGroup
import edgn.lightdb.api.structs.map.LightMapValue
import edgn.lightdb.memory.internal.universal.MemoryRefresh
import edgn.lightdb.memory.internal.universal.mod.MemoryGroup
import java.io.Closeable
import java.util.Optional
import kotlin.reflect.KClass

class MemMapGroup : LightMapGroup, Closeable, MemoryRefresh {
    private val container = MemoryGroup<MemMapValue<out Any, out Any>>()

    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightMapValue<String, V>> {
        return get(key, String::class, wrap)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <K : Any, V : Any> get(
        key: String,
        keyType: KClass<K>,
        valueType: KClass<V>
    ): Optional<out LightMapValue<K, V>> {
        return container.get(key)
            .filter { it.keyType == keyType && it.valueType == valueType }
            as Optional<out LightMapValue<K, V>>
    }

    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightMapValue<String, V> {
        return getOrCreate(key, String::class, wrap)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <K : Any, V : Any> getOrCreate(
        key: String,
        keyType: KClass<K>,
        valueType: KClass<V>
    ): LightMapValue<K, V> {
        return container.getOrCreate(key) {
            MemMapValue(keyType, valueType)
        } as LightMapValue<K, V>
    }

    override fun <K : Any, V : Any> drop(key: String, keyType: KClass<K>, valueType: KClass<V>): Boolean {
        return container.get(key).filter {
            it.keyType == keyType && it.valueType == valueType
        }.map { drop(key) }.orElse(false)
    }

    override fun drop(key: String): Boolean {
        return container.remove(key).map {
            it.clear()
            true
        }.orElse(false)
    }

    override fun exists(key: String): Boolean {
        return container.exists(key)
    }

    override fun <K : Any, V : Any> exists(key: String, keyType: KClass<K>, valueType: KClass<V>): Boolean {
        return get(key, keyType, valueType).isPresent
    }

    override fun close() {
        container.close()
    }

    override fun gc() {
        container.removeIf {
            it.modules.available.not()
        }
    }
}
