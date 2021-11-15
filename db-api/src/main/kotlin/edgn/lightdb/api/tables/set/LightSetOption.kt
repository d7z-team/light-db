package edgn.lightdb.api.tables.set

import edgn.lightdb.api.tables.DataOption
import java.util.Optional
import kotlin.reflect.KClass

interface LightSetOption : DataOption<LightSetTable<*>> {
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightSetTable<V>>
    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightSetTable<V>
}
