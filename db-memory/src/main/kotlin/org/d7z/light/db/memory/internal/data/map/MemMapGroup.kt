package org.d7z.light.db.memory.internal.data.map

import org.d7z.light.db.api.structs.map.LightMap
import org.d7z.light.db.api.structs.map.LightMapGroup
import org.d7z.light.db.memory.MemoryRefresh
import org.d7z.light.db.memory.internal.utils.Clear
import org.d7z.light.db.memory.internal.utils.MemoryGroup
import java.util.Optional
import kotlin.reflect.KClass

class MemMapGroup : LightMapGroup, Clear, MemoryRefresh {
    val container = MemoryGroup<MemMapValue<out Any, out Any>>()

    @Suppress("UNCHECKED_CAST")
    override fun <K : Any, V : Any> get(
        key: String,
        keyType: KClass<K>,
        valueType: KClass<V>
    ): Optional<out LightMap<K, V>> {
        return container.get(key)
            .filter { it.keyType == keyType && it.valueType == valueType }
            as Optional<out LightMap<K, V>>
    }

    @Suppress("UNCHECKED_CAST")
    override fun <K : Any, V : Any> getOrCreate(
        key: String,
        keyType: KClass<K>,
        valueType: KClass<V>
    ): LightMap<K, V> {
        return container.getOrCreate(key) {
            MemMapValue(key, keyType, valueType)
        } as LightMap<K, V>
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

    override fun clear() {
        container.clear()
    }

    override fun refresh() {
        container.refresh()
    }
}
