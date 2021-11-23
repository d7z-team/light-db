package edgn.lightdb.memory.internal.data.map

import edgn.lightdb.api.tables.map.LightMapTable
import edgn.lightdb.memory.internal.universal.table.MemoryTable
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

class MMapTable<V : Any>(
    override val key: String,
    override val clazz: KClass<V>
) : MemoryTable<V, MMapValue<V>>(key, clazz), LightMapTable<V> {
    override val value = MMapValue(this)

    val data: ConcurrentHashMap<String, V> = ConcurrentHashMap()

    override fun destroy() {
        data.clear()
    }
}
