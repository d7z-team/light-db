package edgn.lightdb.api.structs.set

import edgn.lightdb.api.structs.DataGroup
import java.util.Optional
import kotlin.reflect.KClass

interface LightSetGroup : DataGroup {
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightSetValue<V>>
    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightSetValue<V>
}
