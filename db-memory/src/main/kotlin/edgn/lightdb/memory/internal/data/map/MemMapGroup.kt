package edgn.lightdb.memory.internal.data.map

import edgn.lightdb.api.structs.map.LightMapGroup
import edgn.lightdb.api.structs.map.LightMapValue
import edgn.lightdb.api.support.config.DataGroupConfig
import java.io.Closeable
import java.util.Optional
import kotlin.reflect.KClass

class MemMapGroup : LightMapGroup, Closeable {
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightMapValue<String, V>> {
        TODO("Not yet implemented")
    }

    override fun <K : Any, V : Any> get(
        key: String,
        keyType: KClass<K>,
        valueType: KClass<V>
    ): Optional<out LightMapValue<K, V>> {
        TODO("Not yet implemented")
    }

    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightMapValue<String, V> {
        TODO("Not yet implemented")
    }

    override fun <K : Any, V : Any> getOrCreate(
        key: String,
        keyType: KClass<K>,
        valueType: KClass<V>
    ): LightMapValue<K, V> {
        TODO("Not yet implemented")
    }

    override fun <K : Any, V : Any> drop(key: String, keyType: KClass<K>, valueType: KClass<V>): Boolean {
        TODO("Not yet implemented")
    }

    override fun <V : Any> drop(key: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun <K : Any, V : Any> exists(key: String, keyType: KClass<K>, valueType: KClass<V>): Boolean {
        TODO("Not yet implemented")
    }

    override fun <V : Any> exists(key: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun <T : DataGroupConfig> options(key: String, configType: KClass<T>): Optional<T> {
        TODO("Not yet implemented")
    }

    override fun <T : DataGroupConfig> defaultOptions(configType: KClass<T>): T {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}
