package edgn.lightdb.memory.internal.data.set

import edgn.lightdb.api.structs.set.LightSet
import edgn.lightdb.memory.internal.utils.IDataModules
import edgn.lightdb.memory.internal.utils.MemoryMeta
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

class MemSetValue<V : Any>(
    key: String,
    override val valueType: KClass<V>
) : LightSet<V>, IDataModules {

    private val value = Any()

    override val meta = MemoryMeta(key)

    override val available: Boolean
        get() = meta.available

    private val container = ConcurrentHashMap<V, Any>()

    override val size: Long
        get() = meta.checkAvailable {
            container.size.toLong()
        }

    override fun clear() {
        container.clear()
    }

    override fun add(data: V) = meta.checkAvailable {
        container.put(data, value) == null
    }

    override fun remove(data: V) = meta.checkAvailable {
        container.remove(data) != null
    }

    override fun contains(data: V) = meta.checkAvailable {
        container.containsKey(data)
    }

    override fun values() = meta.checkAvailable {
        container.keys().iterator()
    }
}
