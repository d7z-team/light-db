package edgn.lightdb.memory.internal.data.set

import edgn.lightdb.api.structs.set.LightSet
import edgn.lightdb.memory.internal.utils.IDataModules
import edgn.lightdb.memory.internal.utils.MemoryMeta
import java.util.concurrent.ConcurrentSkipListSet
import kotlin.reflect.KClass

class MemSetValue<V : Any>(
    override val valueType: KClass<V>
) : LightSet<V>, IDataModules {

    override val meta = MemoryMeta("")

    override val available: Boolean
        get() = meta.available

    private val container = ConcurrentSkipListSet<V>()

    override val size: Long
        get() = meta.checkAvailable {
            container.size.toLong()
        }

    override fun clear() {
        container.clear()
    }

    override fun add(data: V) = meta.checkAvailable {
        container.add(data)
    }

    override fun remove(data: V) = meta.checkAvailable {
        container.remove(data)
    }

    override fun contains(data: V) = meta.checkAvailable {
        container.contains(data)
    }

    override fun values() = meta.checkAvailable {
        container.iterator()
    }
}
