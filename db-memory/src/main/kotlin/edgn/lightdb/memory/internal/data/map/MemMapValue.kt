package edgn.lightdb.memory.internal.data.map

import edgn.lightdb.api.structs.map.LightMapValue
import edgn.lightdb.api.support.config.DataValueOption
import edgn.lightdb.memory.internal.universal.mod.IModules
import edgn.lightdb.memory.internal.universal.mod.MemoryModules
import edgn.lightdb.memory.internal.universal.opt.MemOptions
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

class MemMapValue<K : Any, V : Any>(
    override val keyType: KClass<K>,
    override val valueType: KClass<V>
) : LightMapValue<K, V>, IModules {

    override val modules: MemoryModules = MemoryModules()

    private val options = MemOptions(modules)

    override fun <T : DataValueOption> option(option: KClass<T>) = options.option(option)

    private val container = ConcurrentHashMap<K, V>()

    override fun clear() {
        container.clear()
    }

    override fun put(key: K, value: V) = modules.checkAvailable {
        Optional.ofNullable(container.put(key, value))
    }

    override fun putIfAbsent(key: K, value: V) = modules.checkAvailable {
        container.putIfAbsent(key, value) ?: value
    }

    override fun getAndSet(key: K, oldValue: V, newValue: V) = modules.checkAvailable {
        container.merge(
            key, newValue
        ) { v1, _ ->
            if (v1 == oldValue) {
                newValue
            } else {
                oldValue
            }
        } == newValue
    }

    override fun merge(key: K, value: V, remapping: (oldValue: V, newValue: V) -> V) = modules.checkAvailable {
        container.merge(key, value, remapping) ?: throw RuntimeException("发生不可预知的错误，此时不应该为 null")
    }

    override fun containsKey(key: K) = modules.checkAvailable {
        container.containsKey(key)
    }

    override fun get(key: K) = modules.checkAvailable {
        Optional.ofNullable(container[key])
    }

    override fun keys() = modules.checkAvailable {
        MapKeysIterator(this)
    }

    class MapKeysIterator<K : Any, V : Any>(
        private val data: MemMapValue<K, V>,
    ) : Iterator<K> {
        private val keys = data.container.keys().asIterator()

        override fun hasNext(): Boolean {
            return data.modules.checkAvailable {
                keys.hasNext()
            }
        }

        override fun next(): K {
            return data.modules.checkAvailable {
                keys.next()
            }
        }
    }

    override val size: Long
        get() = container.size.toLong()
}
