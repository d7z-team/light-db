package edgn.lightdb.memory.internal.impl.list

import edgn.lightdb.api.tables.list.LightListTable
import edgn.lightdb.memory.internal.universal.table.MemoryTable
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.reflect.KClass

class MListTable<V : Any>(
    override val key: String,
    override val clazz: KClass<V>
) : MemoryTable<V, MListValue<V>>(key, clazz), LightListTable<V> {
    override val value = MListValue(this)

    val data = CopyOnWriteArrayList<V>()

    override fun destroy() {
        data.clear()
    }
}
