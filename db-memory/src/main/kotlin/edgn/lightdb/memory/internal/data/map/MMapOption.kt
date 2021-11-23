package edgn.lightdb.memory.internal.data.map

import edgn.lightdb.api.tables.DataConfig
import edgn.lightdb.api.tables.map.LightMapOption
import edgn.lightdb.api.tables.map.LightMapTable
import edgn.lightdb.memory.MemoryDataConfig
import edgn.lightdb.memory.internal.universal.DataRefresh
import edgn.lightdb.memory.internal.universal.MemoryOption
import edgn.lightdb.memory.internal.universal.TableCreate
import edgn.lightdb.memory.internal.universal.table.EmptyMemoryTable
import java.io.Closeable
import java.util.Optional
import kotlin.reflect.KClass

class MMapOption(config: MemoryDataConfig) : LightMapOption, DataRefresh, Closeable {
    private val internal = MemoryOption(
        config,
        create = object : TableCreate {
            override fun <V : Any> new(key: String, wrap: KClass<V>): EmptyMemoryTable<V> {
                return MMapTable(key, wrap)
            }
        }
    )

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightMapTable<V>> {
        return internal.get(key, wrap) as Optional<out LightMapTable<V>>
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightMapTable<V> {
        return internal.getOrCreate(key, wrap) as LightMapTable<V>
    }

    override val config: DataConfig
        get() = internal.config

    override fun <V : Any> drop(key: String, wrap: KClass<V>) = internal.drop(key, wrap)

    override fun <V : Any> exists(key: String, wrap: KClass<V>) = internal.exists(key, wrap)

    override fun refresh() = internal.refresh()

    override fun close() = internal.close()
}
