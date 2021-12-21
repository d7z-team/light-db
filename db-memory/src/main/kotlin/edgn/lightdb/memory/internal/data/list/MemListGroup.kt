package edgn.lightdb.memory.internal.data.list

import edgn.lightdb.api.structs.list.LightListGroup
import edgn.lightdb.api.structs.list.LightListValue
import edgn.lightdb.api.support.config.DataGroupConfig
import java.io.Closeable
import java.util.Optional
import kotlin.reflect.KClass

class MemListGroup : LightListGroup, Closeable {
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightListValue<V>> {
        TODO("Not yet implemented")
    }

    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightListValue<V> {
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
