package edgn.lightdb.memory.internal.impl.map

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
