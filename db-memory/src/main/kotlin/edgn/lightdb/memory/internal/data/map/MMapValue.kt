package edgn.lightdb.memory.internal.data.map

import edgn.lightdb.api.tables.map.LightMapValue
import java.util.Optional

class MMapValue<V : Any>(private val table: MMapTable<V>) : LightMapValue<V> {
    override val size: Long
        get() = table.checkDestroy {
            table.data.size.toLong()
        }

    override fun clear() = table.checkDestroy {
        table.data.clear()
    }

    override fun put(key: String, value: V): Optional<V> = table.checkDestroy {
        Optional.ofNullable(table.data.put(key, value))
    }

    override fun putIfAbsent(key: String, value: V): V = table.checkDestroy {
        table.data.putIfAbsent(key, value) ?: value
    }

    override fun containsKey(key: String): Boolean = table.checkDestroy {
        table.data.containsKey(key)
    }

    override fun merge(key: String, value: V, remapping: (oldValue: V, newValue: V) -> V): V = table.checkDestroy {
        table.data.merge(key, value, remapping) ?: throw RuntimeException("发生不可预知的错误，此时不应该为 null")
    }

    override fun getAndSet(key: String, oldValue: V, newValue: V): Boolean = table.checkDestroy {
        table.data.merge(
            key, newValue
        ) { v1, _ ->
            if (v1 == oldValue) {
                newValue
            } else {
                oldValue
            }
        } == newValue
    }

    override fun get(key: String): Optional<V> = table.checkDestroy {
        Optional.ofNullable(table.data[key])
    }

    override fun keys(): Iterator<String> = table.checkDestroy {
        MapKeysIterator(this)
    }

    class MapKeysIterator<V : Any>(
        private val data: MMapValue<V>,
    ) : Iterator<String> {
        private val keys = data.table.data.keys().asIterator()

        override fun hasNext(): Boolean {
            return data.table.checkDestroy {
                keys.hasNext()
            }
        }

        override fun next(): String {
            return data.table.checkDestroy {
                keys.next()
            }
        }
    }
}
