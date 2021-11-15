package edgn.lightdb.api.tables.list

import edgn.lightdb.api.tables.DataOption
import java.util.Optional
import kotlin.reflect.KClass

interface LightListOption : DataOption<LightListTable<out Any>> {
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightListTable<V>>

    override fun <V : Any> getOrCreate(
        key: String,
        wrap: KClass<V>,
    ): LightListTable<V>
}
