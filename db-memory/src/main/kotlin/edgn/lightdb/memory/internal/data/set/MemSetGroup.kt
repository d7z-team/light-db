package edgn.lightdb.memory.internal.data.set

import edgn.lightdb.api.structs.set.LightSet
import edgn.lightdb.api.structs.set.LightSetGroup
import edgn.lightdb.memory.MemoryRefresh
import edgn.lightdb.memory.internal.utils.MemoryGroup
import java.io.Closeable
import java.util.Optional
import kotlin.reflect.KClass

class MemSetGroup : LightSetGroup, Closeable, MemoryRefresh {
    private val container = MemoryGroup<MemSetValue<out Any>>()

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightSet<V>> {
        return container.get(key)
            .filter { it.valueType == wrap } as Optional<out LightSet<V>>
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightSet<V> {
        return container.getOrCreate(key) {
            MemSetValue(wrap)
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

    override fun close() {
        container.close()
    }
}
