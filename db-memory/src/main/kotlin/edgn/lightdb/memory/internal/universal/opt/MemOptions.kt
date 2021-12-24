package edgn.lightdb.memory.internal.universal.opt

import edgn.lightdb.api.support.config.DataValueOption
import edgn.lightdb.api.support.config.DataValueOptions
import edgn.lightdb.api.support.config.exmaple.ExpiredOption
import edgn.lightdb.api.support.module.ValueModules
import java.util.Optional
import kotlin.reflect.KClass

class MemOptions(modules: ValueModules) : DataValueOptions {

    /**
     *  Memory 支持的额外配置
     */
    private val options = mapOf<KClass<*>, DataValueOption>(
        MemExpiredOption::class to MemExpiredOption(modules),
        ExpiredOption::class to MemExpiredOption(modules)
    )

    @Suppress("UNCHECKED_CAST")
    override fun <T : DataValueOption> option(option: KClass<T>): Optional<T> {
        return Optional.ofNullable(options[option]) as Optional<T>
    }
}
