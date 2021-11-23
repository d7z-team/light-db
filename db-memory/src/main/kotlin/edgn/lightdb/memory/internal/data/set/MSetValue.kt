package edgn.lightdb.memory.internal.data.set

import edgn.lightdb.api.tables.set.LightSetValue

class MSetValue<V : Any>(private val table: MSetTable<V>) : LightSetValue<V> {
    override val size: Long
        get() = table.checkDestroy {
            table.data.size.toLong()
        }

    override fun clear() = table.checkDestroy {
        table.data.clear()
    }

    override fun add(data: V): Boolean = table.checkDestroy {
        table.data.add(data)
    }

    override fun remove(data: V): Boolean = table.checkDestroy {
        table.data.remove(data)
    }

    override fun contains(data: V): Boolean = table.checkDestroy {
        table.data.contains(data)
    }

    override fun values(): Iterator<V> = table.checkDestroy {
        SetIterator(this)
    }

    class SetIterator<V : Any>(
        private val data: MSetValue<V>
    ) : Iterator<V> {
        private val iterator = data.table.data.iterator()
        override fun hasNext(): Boolean = data.table.checkDestroy {
            iterator.hasNext()
        }

        override fun next(): V = data.table.checkDestroy {
            iterator.next()
        }
    }
}
