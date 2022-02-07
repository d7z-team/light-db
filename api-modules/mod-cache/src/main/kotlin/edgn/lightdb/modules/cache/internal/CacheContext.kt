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

    override fun execute(): V {
        val data = content.get(key)
        if (data.isPresent) {
            return data.get()
        }
        val item = invoke() ?: defaultValue.get().orElseThrow() // 执行缓存方法，如不存在则使用默认值
        content.saveIfAbsent(key, item)
        return item
    }

    override fun execute(defaultReturn: V): V {
        return content.get(key).orElse(defaultReturn)
    }
}
