package edgn.lightdb.memory.internal.impl.list

import edgn.lightdb.api.tables.DataConfig
import edgn.lightdb.api.tables.list.LightListOption
import edgn.lightdb.api.tables.list.LightListTable
import edgn.lightdb.memory.MemoryDataConfig
import edgn.lightdb.memory.internal.universal.DataRefresh
import edgn.lightdb.memory.internal.universal.MemoryOption
import edgn.lightdb.memory.internal.universal.TableCreate
import edgn.lightdb.memory.internal.universal.table.EmptyMemoryTable
import java.io.Closeable
import java.util.Optional
import kotlin.reflect.KClass

class MListOption(config: MemoryDataConfig) : LightListOption, DataRefresh, Closeable {
    private val internal = MemoryOption(
        config,
        create = object : TableCreate {
            override fun <V : Any> new(key: String, wrap: KClass<V>): EmptyMemoryTable<V> {
                return MListTable(key, wrap)
            }
        }
    )

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightListTable<V>> {
        return internal.get(key, wrap) as Optional<out LightListTable<V>>
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightListTable<V> {
        return internal.getOrCreate(key, wrap) as LightListTable<V>
    }

    override val config: DataConfig
        get() = internal.config

    override fun <V : Any> drop(key: String, wrap: KClass<V>) = internal.drop(key, wrap)

    override fun <V : Any> exists(key: String, wrap: KClass<V>) = internal.exists(key, wrap)

    override fun refresh() = internal.refresh()

    override fun close() = internal.close()
}
