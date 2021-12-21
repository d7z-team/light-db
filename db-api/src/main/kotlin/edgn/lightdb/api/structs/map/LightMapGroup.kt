package edgn.lightdb.api.structs.map

import edgn.lightdb.api.structs.DataGroup
import java.util.Optional
import kotlin.reflect.KClass

interface LightMapGroup : DataGroup {
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightMapValue<String, V>>
    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightMapValue<String, V>
    fun <K : Any, V : Any> get(
        key: String,
        keyType: KClass<K>,
        valueType: KClass<V>
    ): Optional<out LightMapValue<K, V>>

    fun <K : Any, V : Any> getOrCreate(
        key: String,
        keyType: KClass<K>,
        valueType: KClass<V>
    ): LightMapValue<K, V>

    fun <K : Any, V : Any> drop(
        key: String,
        keyType: KClass<K>,
        valueType: KClass<V>
    ): Boolean

    fun <K : Any, V : Any> exists(
        key: String,
        keyType: KClass<K>,
        valueType: KClass<V>
    ): Boolean
}
