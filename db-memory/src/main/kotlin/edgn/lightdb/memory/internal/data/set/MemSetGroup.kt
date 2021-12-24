package edgn.lightdb.memory.internal.data.set

import edgn.lightdb.api.structs.set.LightSetGroup
import edgn.lightdb.api.structs.set.LightSetValue
import edgn.lightdb.memory.internal.universal.mod.MemoryGroup
import java.io.Closeable
import java.util.Optional
import kotlin.reflect.KClass

class MemSetGroup : LightSetGroup, Closeable {
    private val container = MemoryGroup<MemSetValue<out Any>>()

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightSetValue<V>> {
        return container.get(key)
            .filter { it.valueType == wrap } as Optional<out LightSetValue<V>>
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightSetValue<V> {
        return container.getOrCreate(key) {
            MemSetValue(wrap)
        } as LightSetValue<V>
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
