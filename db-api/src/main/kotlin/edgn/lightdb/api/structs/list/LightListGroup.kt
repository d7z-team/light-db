package edgn.lightdb.api.structs.list

import edgn.lightdb.api.structs.DataGroup
import java.util.Optional
import kotlin.reflect.KClass

interface LightListGroup : DataGroup {
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightListValue<V>>
    override fun <V : Any> getOrCreate(
        key: String,
        wrap: KClass<V>,
    ): LightListValue<V>
}
