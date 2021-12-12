package edgn.lightdb.api.tables.set

import edgn.lightdb.api.tables.DataNamespace
import java.util.Optional
import kotlin.reflect.KClass

interface LightSetNamespace : DataNamespace {
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightSetTable<V>>
    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightSetTable<V>
}
