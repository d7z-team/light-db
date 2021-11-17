package edgn.lightdb.memory.internal.universal.table

import edgn.lightdb.api.tables.DataValue
import java.util.Optional
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.reflect.KClass

abstract class MemoryTable<V : Any, WRAP : DataValue<V>>(
    override val key: String,
    override val clazz: KClass<V>
) : EmptyMemoryTable<V>() {
    /**
     * 包装对象
     */
    abstract val value: WRAP
    private val delete = AtomicBoolean(false)
    override val available: Boolean
        get() = expired.not() && delete.get().not()

    override fun items(): Optional<WRAP> {
        return if (available) {
            Optional.of(value)
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
        mutableMapOf<String, String>().clear()
        destroy()
    }

    /**
     * 数据销毁方法
     */
    abstract fun destroy()
}
