package edgn.lightdb.modules.cache.internal

import edgn.lightdb.modules.cache.api.ICacheContent
import edgn.lightdb.modules.cache.api.ICacheContext
import java.util.Optional
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.atomic.AtomicReference

/**
 * 缓存操作上下文
 */
class CacheContext<K : Any, V : Any>(
    private val content: ICacheContent<K, V>,
    private val key: K,
    private val invoke: () -> V?,
) : ICacheContext<K, V> {
    private val filterList = LinkedBlockingQueue<(V) -> Boolean>()
    private val defaultValue = AtomicReference<Optional<V>>(Optional.empty())
    override fun filter(filter: (V) -> Boolean): ICacheContext<K, V> {
        filterList.add(filter)
        return this
    }

    override fun default(def: V): ICacheContext<K, V> {
        defaultValue.set(Optional.of(def))
        return this
    }

    override fun execute(defaultFun: () -> V): V {
        return content.get(key).or {
            val funcResult = invoke() // 无缓存，执行业务拉取缓存
            if ((funcResult != null) && filterList.all { it(funcResult) }) { // 缓存通过过滤器
                content.saveIfAbsent(key, funcResult)
                Optional.of(funcResult) // 数据缓存
            } else if (funcResult != null) {
                Optional.of(funcResult) // 如果 funcRes 不为空则表示被拦截器拦截，此时数据不缓存
            } else {
                val def = defaultValue.get()
                if (def.isPresent) {
                    content.saveIfAbsent(key, def.get()) // 缓存默认值
                    Optional.of(def.get())
                } else {
                    Optional.empty() // 无缓存无默认值
                }
            }
        }.orElseGet(defaultFun)
    }

    override fun execute(defaultReturn: V): V {
        return execute { defaultReturn }
    }
}
