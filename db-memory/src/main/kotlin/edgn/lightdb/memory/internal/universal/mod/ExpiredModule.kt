package edgn.lightdb.memory.internal.universal.mod

import edgn.lightdb.api.support.module.ValueModule
import java.time.LocalDateTime
import java.util.concurrent.atomic.AtomicReference

/**
 * 数据过期模块
 */
class ExpiredModule : ValueModule {

    /**
     * 过期时间，当系统时间超过此时间后将过期
     */
    val timeout = AtomicReference(LocalDateTime.MAX)
    override val available: Boolean
        get() = timeout.get().isAfter(LocalDateTime.now())

    override fun bind() {
    }

    override fun unbind() {
    }
}
