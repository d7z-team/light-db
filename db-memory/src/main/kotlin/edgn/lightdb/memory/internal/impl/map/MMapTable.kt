package edgn.lightdb.memory.internal.impl.map

import edgn.lightdb.api.tables.map.LightMapTable
import edgn.lightdb.memory.internal.universal.MemoryDataTable
import edgn.lightdb.memory.internal.universal.TableDelete
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.reflect.KClass

class MMapTable<V : Any>(
    override val key: String,
    override val clazz: KClass<V>
) : MemoryDataTable<V>(), LightMapTable<V>, TableDelete {
    private val listValue = MMapValue(this)
    private val delete = AtomicBoolean(false)

    /**
     * 存储对象
     */
    val data = ConcurrentHashMap<String, V>()
    override val available: Boolean
        get() = expired.not() && delete.get().not()

    override fun get(): Optional<MMapValue<V>> {
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
