package edgn.lightdb.memory.internal.impl.set

import edgn.lightdb.api.tables.set.LightSetTable
import edgn.lightdb.memory.internal.universal.MemoryDataTable
import edgn.lightdb.memory.internal.universal.TableDelete
import java.util.Optional
import java.util.concurrent.ConcurrentSkipListSet
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.reflect.KClass

class MSetTable<V : Any>(
    override val key: String,
    override val clazz: KClass<V>
) : MemoryDataTable<V>(), LightSetTable<V>, TableDelete {
    private val listValue = MSetValue(this)
    private val delete = AtomicBoolean(false)

    /**
     * 存储对象
     */
    val data = ConcurrentSkipListSet<V>()
    override val available: Boolean
        get() = expired.not() && delete.get().not()

    override fun items(): Optional<MSetValue<V>> {
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
