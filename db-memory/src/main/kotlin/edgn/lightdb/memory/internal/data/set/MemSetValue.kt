package edgn.lightdb.memory.internal.data.set

import edgn.lightdb.api.structs.set.LightSetValue
import edgn.lightdb.api.support.config.DataValueOption
import edgn.lightdb.memory.internal.universal.mod.IModules
import edgn.lightdb.memory.internal.universal.mod.MemoryModules
import edgn.lightdb.memory.internal.universal.opt.MemOptions
import java.util.concurrent.ConcurrentSkipListSet
import kotlin.reflect.KClass

class MemSetValue<V : Any>(
    override val valueType: KClass<V>
) : LightSetValue<V>, IModules {
    override val modules: MemoryModules = MemoryModules()

    private val options = MemOptions(modules)

    override fun <T : DataValueOption> option(option: KClass<T>) = options.option(option)

    private val container = ConcurrentSkipListSet<V>()
    override val size: Long
        get() = modules.checkAvailable {
            container.size.toLong()
        }

    override fun clear() {
        container.clear()
    }

    override fun add(data: V) = modules.checkAvailable {
        container.add(data)
    }

    override fun remove(data: V) = modules.checkAvailable {
        container.remove(data)
    }

    override fun contains(data: V) = modules.checkAvailable {
        container.contains(data)
    }

    override fun values() = modules.checkAvailable {
        container.iterator()
    }
}
