package edgn.lightdb.memory.internal.impl.set

import edgn.lightdb.api.tables.set.LightSetTable
import edgn.lightdb.memory.internal.universal.table.MemoryTable
import java.util.concurrent.ConcurrentSkipListSet
import kotlin.reflect.KClass

class MSetTable<V : Any>(
    override val key: String,
    override val clazz: KClass<V>
) : MemoryTable<V, MSetValue<V>>(key, clazz), LightSetTable<V> {
    override val value = MSetValue(this)

    /**
     * 存储对象
     */
    val data = ConcurrentSkipListSet<V>()

    override fun destroy() {
        value.clear()
    }
}
