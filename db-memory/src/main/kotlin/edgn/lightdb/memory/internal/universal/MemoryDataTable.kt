package edgn.lightdb.memory.internal.universal

import edgn.lightdb.api.DestroyException
import edgn.lightdb.api.tables.DataTable
import java.time.Duration
import java.time.Instant
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

abstract class MemoryDataTable<T : Any> : DataTable<T> {
    /**
     * 内部计时器 ,此值代表过期时间
     */
    private val expireData = AtomicLong(Long.MAX_VALUE)

    /**
     * 如果为 true 则表示已过期
     */
    val expired: Boolean
        get() = expireData.get() < utcDate

    inline fun <T : Any> checkDestroy(func: () -> T): T {
        return if (available) {
            func()
        } else {
            throw DestroyException("实例 $key 已被销毁.")
        }
    }

    override fun expire(unit: TimeUnit): Long = checkDestroy {
        unit.convert(Duration.ofMillis(expireData.get()))
    }

    override fun expire(timeout: Long, unit: TimeUnit) = checkDestroy {
        expireData.set(unit.toMillis(timeout))
    }

    private val utcDate: Long
        get() = Instant.now().toEpochMilli()
}
