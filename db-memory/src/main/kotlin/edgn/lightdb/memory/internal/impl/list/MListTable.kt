package edgn.lightdb.memory.internal.impl.list

import edgn.lightdb.api.tables.list.LightListTable
import edgn.lightdb.memory.internal.universal.MemoryDataTable
import edgn.lightdb.memory.internal.universal.TableDelete
import java.util.Optional
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.reflect.KClass

class MListTable<V : Any>(
    override val key: String,
    override val clazz: KClass<V>
) : MemoryDataTable<V>(), LightListTable<V>, TableDelete {
    private val listValue = MListValue(this)
    private val delete = AtomicBoolean(false)

    /**
     * 存储对象
     */
    val data = CopyOnWriteArrayList<V>()
    override val available: Boolean
        get() = expired.not() && delete.get().not()

    override fun items(): Optional<MListValue<V>> {
        return if (available) {
            Optional.of(listValue)
        } else {
            Optional.empty()
        }
    }

    @Synchronized
    override fun delete() {
        if (delete.get()) {
            return
        }
        delete.set(true)
        data.clear()
    }
}