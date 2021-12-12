package edgn.lightdb.api.tables.list

import edgn.lightdb.api.tables.DataNamespace
import java.util.Optional
import kotlin.reflect.KClass

interface LightListNamespace : DataNamespace {
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightListTable<V>>
    override fun <V : Any> getOrCreate(
        key: String,
        wrap: KClass<V>,
    ): LightListTable<V>
}
