package edgn.lightdb.memory.internal.data.set

import edgn.lightdb.api.structs.set.LightSetGroup
import edgn.lightdb.api.structs.set.LightSetValue
import edgn.lightdb.api.support.config.DataGroupConfig
import java.io.Closeable
import java.util.Optional
import kotlin.reflect.KClass

class MemSetGroup : LightSetGroup, Closeable {
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightSetValue<V>> {
        TODO("Not yet implemented")
    }

    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightSetValue<V> {
        TODO("Not yet implemented")
    }

    override fun <V : Any> drop(key: String): Boolean {
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
