package edgn.lightdb.memory.internal.universal.table

import edgn.lightdb.api.DestroyException
import edgn.lightdb.api.tables.DataTable
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

/**
 * 过期相关实现
 */
abstract class ExpireMemoryTable<T : Any> : DataTable<T> {
    /**
     * 内部计时器 ,此值代表过期时间 (目标秒) ,如果为 -1 则表示永不过期
     */
    private val expireData = AtomicLong(-1)

    /**
     * 如果为 true 则表示已过期
     */
    val expired: Boolean
        get() = expireData.get() != -1L && expireData.get() < utcDate

    inline fun <T : Any> checkDestroy(func: () -> T): T {
        return if (available) {
            func()
        } else {
            throw DestroyException("实例 $key 已被销毁.")
        }
    }

    override fun expire(unit: TimeUnit): Long = checkDestroy {
        if (expireData.get() == -1L) {
            -1L
        } else {
            unit.convert(expireData.get() - utcDate, TimeUnit.SECONDS)
        }
    }

    override fun clearExpire() = checkDestroy {
        expireData.set(-1L)
    }

    override fun expire(timeout: Long, unit: TimeUnit) = checkDestroy {
        if (timeout < 0) {
            // 强制过期
            expireData.set(utcDate - 1)
        } else {
            expireData.set(unit.toSeconds(timeout))
        }
    }

    abstract fun delete()

    private val utcDate: Long
        get() = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)
}
