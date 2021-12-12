package edgn.lightdb.api.tables.map

import edgn.lightdb.api.tables.DataNamespace
import java.util.Optional
import kotlin.reflect.KClass

interface LightMapNamespace : DataNamespace {
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightMapTable<V>>
    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightMapTable<V>
}
