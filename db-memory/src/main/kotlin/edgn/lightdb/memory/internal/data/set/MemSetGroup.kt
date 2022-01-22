package edgn.lightdb.memory.internal.data.set

import edgn.lightdb.api.structs.set.LightSet
import edgn.lightdb.api.structs.set.LightSetGroup
import edgn.lightdb.memory.MemoryRefresh
import edgn.lightdb.memory.internal.utils.Clear
import edgn.lightdb.memory.internal.utils.MemoryGroup
import java.util.Optional
import kotlin.reflect.KClass

class MemSetGroup : LightSetGroup, Clear, MemoryRefresh {
    val container = MemoryGroup<MemSetValue<out Any>>()

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightSet<V>> {
        return container.get(key)
            .filter { it.valueType == wrap } as Optional<out LightSet<V>>
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightSet<V> {
        return container.getOrCreate(key) {
            MemSetValue(key, wrap)
        } as LightSet<V>
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

    override fun refresh() {
        container.removeIf {
            it.available.not()
        }
    }

    override fun clear() {
        container.clear()
    }
}
