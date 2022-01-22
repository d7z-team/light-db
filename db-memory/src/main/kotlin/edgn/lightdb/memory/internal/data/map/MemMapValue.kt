package edgn.lightdb.memory.internal.data.map

import edgn.lightdb.api.structs.map.LightMap
import edgn.lightdb.memory.internal.utils.IDataModules
import edgn.lightdb.memory.internal.utils.MemoryMeta
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

class MemMapValue<K : Any, V : Any>(
    key: String,
    override val keyType: KClass<K>,
    override val valueType: KClass<V>
) : LightMap<K, V>, IDataModules {

    override val meta: MemoryMeta = MemoryMeta(key)

    override val available: Boolean
        get() = meta.available

    private val container = ConcurrentHashMap<K, V>()

    override fun clear() {
        container.clear()
    }

    override fun put(key: K, value: V) = meta.checkAvailable {
        Optional.ofNullable(container.put(key, value))
    }

    override fun putIfAbsent(key: K, value: V) = meta.checkAvailable {
        container.putIfAbsent(key, value) ?: value
    }

    override fun compareAndSwap(key: K, oldValue: V, newValue: V) = meta.checkAvailable {
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

    override fun containsKey(key: K) = meta.checkAvailable {
        container.containsKey(key)
    }

    override fun get(key: K) = meta.checkAvailable {
        Optional.ofNullable(container[key])
    }

    override fun removeKey(key: K): Optional<V> = meta.checkAvailable {
        Optional.ofNullable(container.remove(key))
    }

    override fun keys() = meta.checkAvailable {
        MapKeysIterator(this)
    }

    class MapKeysIterator<K : Any, V : Any>(
        private val data: MemMapValue<K, V>,
    ) : Iterator<K> {
        private val keys = data.container.keys().asIterator()

        override fun hasNext(): Boolean {
            return data.meta.checkAvailable {
                keys.hasNext()
            }
        }

        override fun next(): K {
            return data.meta.checkAvailable {
                keys.next()
            }
        }
    }

    override val size: Long
        get() = container.size.toLong()
}
