package edgn.lightdb.memory.internal.universal.opt

import edgn.lightdb.api.support.config.exmaple.ExpiredOption
import edgn.lightdb.api.support.module.ValueModules
import edgn.lightdb.memory.internal.universal.mod.ExpiredModule
import java.lang.Long.max
import java.time.Duration
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

class MemExpiredOption(private val modules: ValueModules) : ExpiredOption {
    override fun expired(unit: TimeUnit): Long {
        return modules.module(ExpiredModule::class).map {
            // 计算过期时间 (秒)
            unit.convert(Duration.between(LocalDateTime.now(), it.timeout.get()).toSeconds(), TimeUnit.SECONDS)
        }.map { max(0L, it) }.orElse(-1)
    }

    override fun expired(date: Long, unit: TimeUnit) {
        modules.module(ExpiredModule::class).orElse(
            modules.bind(ExpiredModule())
        ).timeout.set(LocalDateTime.now().plusSeconds(unit.toSeconds(max(0L, date))))
    }

    override fun clearExpired() {
        modules.unbind(ExpiredModule::class)
    }
}
