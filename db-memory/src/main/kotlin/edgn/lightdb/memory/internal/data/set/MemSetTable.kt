package edgn.lightdb.memory.internal.data.set

import edgn.lightdb.api.tables.set.LightSetTable
import edgn.lightdb.memory.internal.universal.table.MemoryTable
import java.util.concurrent.ConcurrentSkipListSet
import kotlin.reflect.KClass

class MemSetTable<V : Any>(
    override val key: String,
    override val clazz: KClass<V>
) : MemoryTable<V, MemSetValue<V>>(key, clazz), LightSetTable<V> {
    override val value = MemSetValue(this)

    /**
     * 存储对象
     */
    val data = ConcurrentSkipListSet<V>()

    override fun destroy() {
        value.clear()
    }
}
