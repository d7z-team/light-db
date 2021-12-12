package edgn.lightdb.utils.universal

import edgn.lightdb.api.tables.DataConfig
import edgn.lightdb.api.tables.DataNamespace
import edgn.lightdb.api.tables.DataTable
import java.util.Optional
import kotlin.reflect.KClass

class SimpleOptions : DataNamespace {
    override val config: DataConfig
        get() = TODO("Not yet implemented")

    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out DataTable<V>> {
        TODO("Not yet implemented")
    }

    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): DataTable<V> {
        TODO("Not yet implemented")
    }

    override fun <V : Any> drop(key: String, wrap: KClass<V>): Boolean {
        TODO("Not yet implemented")
    }

    override fun <V : Any> exists(key: String, wrap: KClass<V>): Boolean {
        TODO("Not yet implemented")
    }
}
