package edgn.lightdb.memory.internal.universal.mod

import edgn.lightdb.api.support.module.ValueModule
import edgn.lightdb.api.support.module.ValueModules
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

/**
 * 模块加载器
 */
class MemoryModules : ValueModules {
    private val maps = ConcurrentHashMap<KClass<out ValueModule>, ValueModule>()
    override val available: Boolean
        get() = maps.none { it.value.available.not() }

    @Synchronized
    override fun <B : ValueModule> bind(module: B): B {
        maps.put(module::class, module)?.unbind()
        module.bind()
        return module
    }

    @Synchronized
    override fun unbind(clazz: KClass<out ValueModule>): Boolean {
        val dataValue = maps.remove(clazz)
        dataValue?.unbind()
        return dataValue != null
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ValueModule> module(option: KClass<T>): Optional<T> {
        return Optional.ofNullable(maps[option]) as Optional<T>
    }

    @Synchronized
    override fun close() {
        maps.values.forEach { it.unbind() }
        maps.clear()
    }
}
