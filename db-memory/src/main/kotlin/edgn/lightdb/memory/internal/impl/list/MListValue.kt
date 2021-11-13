package edgn.lightdb.memory.internal.impl.list

import edgn.lightdb.api.tables.list.LightListValue
import java.util.Optional

class MListValue<V : Any>(private val table: MListTable<V>) : LightListValue<V> {
    override val size: Long
        get() = table.checkDestroy { table.data.size.toLong() }

    override fun clear() = table.checkDestroy {
        table.data.clear()
    }

    override fun add(element: V): Unit = table.checkDestroy {
        table.data.add(element)
    }

    override fun add(index: Long, element: V) = table.checkDestroy {
        table.data.add(index.toInt(), element)
    }

    override fun remove(index: Long) = table.checkDestroy {
        if (index >= size) {
            Optional.empty()
        } else {
            try {
                Optional.ofNullable(table.data.removeAt(index.toInt()))
            } catch (e: IndexOutOfBoundsException) {
                Optional.empty()
            }
        }
    }

    override fun set(index: Long, element: V): V = table.checkDestroy {
        table.data.set(index.toInt(), element)
    }

    override fun get(index: Long): Optional<V> = table.checkDestroy {
        try {
            Optional.ofNullable(table.data[index.toInt()])
        } catch (e: IndexOutOfBoundsException) {
            Optional.empty()
        }
    }

    override fun indexOf(element: V): Long = table.checkDestroy {
        table.data.indexOf(element).toLong()
    }

    override fun lastIndexOf(element: V): Long = table.checkDestroy {
        table.data.lastIndexOf(element).toLong()
    }

    override fun values(): MutableIterator<V> = table.checkDestroy {
        LightListValue.LightListValueMutableIterator(this, size)
    }
}
