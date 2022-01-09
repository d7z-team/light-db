package edgn.lightdb.memory.internal.utils

import edgn.lightdb.api.DestroyException
import edgn.lightdb.memory.MemoryMetaData
import java.util.Optional
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

/**
 * 内存LightDB相关配置
 */
class MemoryMeta(private val name: String) : MemoryMetaData {

    /**
     * -1 : no ttl
     *  >0 : System.time
     */
    private val internalExpireDate = AtomicLong(-1)

    val available: Boolean
        get() = internalExpireDate.get() == -1L || internalExpireDate.get() >= currentTime

    /**
     * 当前时间
     */
    private val currentTime: Long
        get() = System.currentTimeMillis() / 1000

    override val ttl: Long
        get() = if (internalExpireDate.get() == -1L) {
            // no ttl
            -1
        } else {
            (internalExpireDate.get() - currentTime).coerceAtLeast(0L)
        }

    override fun expired(ttl: Long, unit: TimeUnit) = checkAvailable {
        internalExpireDate.set(TimeUnit.SECONDS.convert(ttl, unit) + currentTime)
    }

    override fun clearExpire() = checkAvailable {
        internalExpireDate.set(-1)
    }

    fun <T : Any> checkAvailable(function: () -> T): T {
        return if (available) {
            function()
        } else {
            throw DestroyException("实例 \'$name\' 已过期.")
        }
    }

    override fun get(key: String): Optional<String> {
        return when (key.lowercase()) {
            "db.ttl" -> {
                return Optional.of(ttl.toString())
            }
            else -> {
                Optional.empty()
            }
        }
    }

    override fun set(key: String, value: String): Boolean {
        when (key.lowercase()) {
            "db.ttl" -> {
                expired(
                    value.toLongOrNull() ?: throw NumberFormatException("无法格式化 $value."), TimeUnit.SECONDS
                )
            }
            else -> {
                return false
            }
        }
        return true
    }
}
