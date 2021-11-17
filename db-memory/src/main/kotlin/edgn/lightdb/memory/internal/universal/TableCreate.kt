package edgn.lightdb.memory.internal.universal

import edgn.lightdb.memory.internal.universal.table.EmptyMemoryTable
import kotlin.reflect.KClass

@FunctionalInterface
interface TableCreate {
    fun <V : Any> new(key: String, wrap: KClass<V>): EmptyMemoryTable<V>
}
