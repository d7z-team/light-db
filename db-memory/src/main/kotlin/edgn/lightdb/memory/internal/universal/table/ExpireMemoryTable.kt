package edgn.lightdb.memory.internal.universal.table

import edgn.lightdb.api.DestroyException
import edgn.lightdb.api.tables.DataTable
import edgn.lightdb.memory.internal.LogUtils
import edgn.lightdb.memory.internal.getLogger
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
        if (unit.coerceAtLeast(TimeUnit.MILLISECONDS) == TimeUnit.MILLISECONDS) {
            logger.warn(LogUtils.marker, "单位过小可能导致精度丢失,请使用更大单位或使用其他 LightDB 模块以提高精度.")
        }
        val now = utcDate
        if (timeout < 0) {
            // 强制过期
            expireData.set(now - 1)
        } else {
            expireData.set(
                try {
                    Math.addExact(now, unit.toSeconds(timeout))
                } catch (e: Exception) {
                    Long.MAX_VALUE
                }
            )
        }
    }

    abstract fun delete()

    open val utcDate: Long
        get() = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)

    companion object {
        private val logger = getLogger()
    }
}
