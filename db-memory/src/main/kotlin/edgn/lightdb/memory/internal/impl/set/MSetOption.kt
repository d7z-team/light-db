package edgn.lightdb.memory.internal.impl.set

import edgn.lightdb.api.tables.DataConfig
import edgn.lightdb.api.tables.set.LightSetOption
import edgn.lightdb.api.tables.set.LightSetTable
import edgn.lightdb.memory.MemoryDataConfig
import edgn.lightdb.memory.internal.universal.DataRefresh
import edgn.lightdb.memory.internal.universal.MemoryOption
import edgn.lightdb.memory.internal.universal.TableCreate
import edgn.lightdb.memory.internal.universal.table.EmptyMemoryTable
import java.io.Closeable
import java.util.Optional
import kotlin.reflect.KClass

class MSetOption(config: MemoryDataConfig) : LightSetOption, DataRefresh, Closeable {
    private val internal = MemoryOption(
        config,
        create = object : TableCreate {
            override fun <V : Any> new(key: String, wrap: KClass<V>): EmptyMemoryTable<V> {
                return MSetTable(key, wrap)
            }
        }
    )

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightSetTable<V>> {
        return internal.get(key, wrap) as Optional<out LightSetTable<V>>
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightSetTable<V> {
        return internal.getOrCreate(key, wrap) as LightSetTable<V>
    }

    override val config: DataConfig
        get() = internal.config

    override fun <V : Any> drop(key: String, wrap: KClass<V>) = internal.drop(key, wrap)

    override fun <V : Any> exists(key: String, wrap: KClass<V>) = internal.exists(key, wrap)

    override fun refresh() = internal.refresh()

    override fun close() = internal.close()
}
