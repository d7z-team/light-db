package edgn.lightdb.memory.internal.data.list

import edgn.lightdb.api.structs.list.LightList
import edgn.lightdb.api.structs.list.LightListGroup
import edgn.lightdb.memory.MemoryRefresh
import edgn.lightdb.memory.internal.utils.MemoryGroup
import java.io.Closeable
import java.util.Optional
import kotlin.reflect.KClass

class MemListGroup : LightListGroup, MemoryRefresh, Closeable {
    private val container = MemoryGroup<MemListValue<out Any>>()

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightList<V>> {
        return container.get(key)
            .filter { it.valueType == wrap } as Optional<out LightList<V>>
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightList<V> {
        return container.getOrCreate(key) {
            MemListValue(key, wrap)
        } as LightList<V>
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

    override fun close() {
        container.close()
    }

    override fun refresh() {
        container.removeIf {
            it.available.not()
        }
    }
}
