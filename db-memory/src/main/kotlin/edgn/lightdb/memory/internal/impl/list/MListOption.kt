package edgn.lightdb.memory.internal.impl.list

import edgn.lightdb.api.DestroyException
import edgn.lightdb.api.tables.list.LightListOption
import edgn.lightdb.memory.MemoryDataConfig
import edgn.lightdb.memory.internal.refresh.DataRefresh
import edgn.lightdb.memory.internal.universal.MemoryDataTable
import java.io.Closeable
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

class MListOption(config: MemoryDataConfig<MemoryDataTable<Any>>) : LightListOption, DataRefresh, Closeable {
    override val config = MemoryDataConfig<MListTable<*>>(config)

    private val trees = ConcurrentHashMap<String, MListTable<*>>()

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<MListTable<V>> {
        return Optional.ofNullable(trees[genKey(key, wrap)])
            .filter { it.clazz == wrap }
            .filter { it.available }
            as Optional<MListTable<V>>
    }

    private fun genKey(key: String, wrap: KClass<*>): String {
        return "$key#${wrap.qualifiedName ?: "\$innerClass"}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): MListTable<V> {
        val savedKey = genKey(key, wrap)
        val result = trees.getOrPut(savedKey) {
            MListTable(savedKey, wrap)
        }
        if (result.clazz != wrap) {
            throw ClassCastException("${result.clazz} != $wrap")
        }
        if (result.available.not()) {
            throw DestroyException("$key 已过期.")
        }
        config.createHook(result)
        return result as MListTable<V>
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
