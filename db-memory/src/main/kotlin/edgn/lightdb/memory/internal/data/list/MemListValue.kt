package edgn.lightdb.memory.internal.data.list

import edgn.lightdb.api.structs.list.LightListValue
import edgn.lightdb.api.support.config.DataValueOption
import edgn.lightdb.memory.internal.universal.mod.IModules
import edgn.lightdb.memory.internal.universal.mod.MemoryModules
import edgn.lightdb.memory.internal.universal.opt.MemOptions
import java.util.Optional
import java.util.Vector
import kotlin.reflect.KClass

class MemListValue<V : Any>(
    override val valueType: KClass<V>
) : LightListValue<V>, IModules {

    override val modules: MemoryModules = MemoryModules()

    private val options = MemOptions(modules)

    override fun <T : DataValueOption> option(option: KClass<T>) = options.option(option)

    private val container = Vector<V>()

    override val size: Long
        get() = container.size.toLong()

    override fun clear() {
        container.clear()
    }

    override fun add(element: V): Unit = modules.checkAvailable {
        container.add(element)
    }

    override fun add(index: Long, element: V) = modules.checkAvailable {
        container.add(index.toInt(), element)
    }

    override fun remove(index: Long): Optional<V> = modules.checkAvailable {
        try {
            Optional.ofNullable(container.removeAt(index.toInt()))
        } catch (e: Exception) {
            Optional.empty()
        }
    }

    override fun set(index: Long, element: V): V = modules.checkAvailable {
        container.set(index.toInt(), element)
    }

    override fun get(index: Long): Optional<V> = modules.checkAvailable {
        Optional.ofNullable(container[index.toInt()])
    }

    override fun indexOf(element: V): Long = modules.checkAvailable {
        container.indexOf(element).toLong()
    }

    override fun lastIndexOf(element: V): Long = modules.checkAvailable {
        container.lastIndexOf(element).toLong()
    }

    override fun sortWith(comparator: Comparator<V>) = modules.checkAvailable {
        container.sortWith(comparator)
    }

    override fun values(): Iterator<V> = modules.checkAvailable {
        container.iterator()
    }
}
