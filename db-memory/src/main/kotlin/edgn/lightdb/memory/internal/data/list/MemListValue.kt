package edgn.lightdb.memory.internal.data.list

import edgn.lightdb.api.tables.list.LightListValue
import java.util.Optional

class MemListValue<V : Any>(private val table: MemListTable<V>) : LightListValue<V> {
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

    override fun sortWith(comparator: Comparator<V>) = table.checkDestroy {
        table.data.sortWith(comparator)
    }

    override fun values(): Iterator<V> = table.checkDestroy {
        MListIterator(this)
    }

    class MListIterator<V : Any>(
        private val data: MemListValue<V>,
    ) : Iterator<V> {
        private val iterator = data.table.data.iterator()
        override fun hasNext() = data.table.checkDestroy {
            iterator.hasNext()
        }

        override fun next(): V = data.table.checkDestroy {
            iterator.next()
        }
    }
}
