package edgn.lightdb.memory.internal.impl.set

import edgn.lightdb.api.DestroyException
import edgn.lightdb.api.tables.set.LightSetOption
import edgn.lightdb.memory.MemoryDataConfig
import edgn.lightdb.memory.internal.refresh.DataRefresh
import edgn.lightdb.memory.internal.universal.MemoryDataTable
import java.io.Closeable
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

class MSetOption(config: MemoryDataConfig<MemoryDataTable<Any>>) : LightSetOption, DataRefresh, Closeable {
    override val config = MemoryDataConfig<MSetTable<*>>(config)

    private val trees = ConcurrentHashMap<String, MSetTable<*>>()

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<MSetTable<V>> {
        return Optional.ofNullable(trees[genKey(key, wrap)])
            .filter { it.clazz == wrap }
            .filter { it.available }
            as Optional<MSetTable<V>>
    }

    private fun genKey(key: String, wrap: KClass<*>): String {
        return "$key#${wrap.qualifiedName ?: "\$innerClass"}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): MSetTable<V> {
        val savedKey = genKey(key, wrap)
        val result = trees.getOrPut(savedKey) {
            MSetTable(savedKey, wrap)
        }
        if (result.clazz != wrap) {
            throw ClassCastException("${result.clazz} != $wrap")
        }
        if (result.available.not()) {
            throw DestroyException("$key 已过期.")
        }
        config.createHook(result)
        return result as MSetTable<V>
    }

    override fun <V : Any> drop(key: String, wrap: KClass<V>): Boolean {
        return get(key, wrap)
            .filter { it.available }
            .map {
                it.delete()
                trees.remove(genKey(key, wrap))
                true
            }.orElse(false)
    }

    override fun <V : Any> exists(key: String, wrap: KClass<V>): Boolean {
        return Optional.ofNullable(trees[genKey(key, wrap)])
            .filter { it.clazz == wrap }
            .filter { it.available }.isPresent
    }

    override fun refresh() {
        trees.filter { it.value.available.not() }
            .map { it.key }.forEach {
                trees.remove(it)
            }
    }

    override fun close() {
        trees.forEach { (_, u) ->
            u.delete()
        }
        trees.clear()
    }
}
