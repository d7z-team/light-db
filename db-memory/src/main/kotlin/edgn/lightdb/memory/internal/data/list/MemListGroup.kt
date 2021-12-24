package edgn.lightdb.memory.internal.data.list

import edgn.lightdb.api.structs.list.LightListGroup
import edgn.lightdb.api.structs.list.LightListValue
import edgn.lightdb.memory.internal.universal.mod.MemoryGroup
import java.io.Closeable
import java.util.Optional
import kotlin.reflect.KClass

class MemListGroup : LightListGroup, Closeable {
    private val container = MemoryGroup<MemListValue<out Any>>()

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightListValue<V>> {
        return container.get(key)
            .filter { it.valueType == wrap } as Optional<out LightListValue<V>>
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightListValue<V> {
        return container.getOrCreate(key) {
            MemListValue(wrap)
        } as LightListValue<V>
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
}
