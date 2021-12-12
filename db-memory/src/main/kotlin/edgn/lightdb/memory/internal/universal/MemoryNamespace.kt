package edgn.lightdb.memory.internal.universal

import edgn.lightdb.api.DestroyException
import edgn.lightdb.api.tables.DataNamespace
import edgn.lightdb.memory.MemoryDataConfig
import edgn.lightdb.memory.internal.universal.table.EmptyMemoryTable
import java.io.Closeable
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

class MemoryNamespace(
    config: MemoryDataConfig,
    private val create: TableCreate
) :
    DataNamespace,
    DataRefresh,
    Closeable {
    override val config = MemoryDataConfig(config)

    private val trees = ConcurrentHashMap<String, EmptyMemoryTable<*>>()

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out EmptyMemoryTable<V>> {
        return Optional.ofNullable(trees[genKey(key, wrap)])
            .filter { it.clazz == wrap }
            .filter { it.available }
            as Optional<EmptyMemoryTable<V>>
    }

    private fun genKey(key: String, wrap: KClass<*>): String {
        return "$key#${wrap.qualifiedName ?: "\$innerClass"}"
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): EmptyMemoryTable<V> {
        val savedKey = genKey(key, wrap)
        val result = trees.getOrPut(savedKey) {
            create.new(key, wrap)
        }
        if (result.clazz != wrap) {
            throw ClassCastException("${result.clazz} != $wrap")
        }
        if (result.available.not()) {
            throw DestroyException("$key 已过期.")
        }
        config.createHook(result)
        return result as EmptyMemoryTable<V>
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
