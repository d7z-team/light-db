package edgn.lightdb.memory.internal.impl.list

import edgn.lightdb.api.tables.list.LightListOption
import edgn.lightdb.api.tables.list.LightListTable
import edgn.lightdb.memory.internal.refresh.DataRefresh
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

class MListOption : LightListOption, DataRefresh {
    private val trees = ConcurrentHashMap<String, MListTable<*>>()
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<LightListTable<V>> {
        trees
        TODO("Not yet implemented")
    }

    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightListTable<V> {
        TODO("Not yet implemented")
    }

    override fun <V : Any> drop(key: String, wrap: KClass<V>): Boolean {
        TODO("Not yet implemented")
    }

    override fun <V : Any> exists(key: String, wrap: KClass<V>): Boolean {
        TODO("Not yet implemented")
    }

    override fun refresh() {
    }
}
