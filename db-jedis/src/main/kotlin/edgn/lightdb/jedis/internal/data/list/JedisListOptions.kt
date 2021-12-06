package edgn.lightdb.jedis.internal.data.list

import edgn.lightdb.api.tables.list.LightListOption
import edgn.lightdb.api.tables.list.LightListTable
import edgn.lightdb.jedis.JedisDataConfig
import java.util.Optional
import kotlin.reflect.KClass

class JedisListOptions(
    override val config: JedisDataConfig
) : LightListOption {

    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightListTable<V>> {
        TODO()
    }

    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightListTable<V> {
        TODO("Not yet implemented")
    }

    override fun <V : Any> drop(key: String, wrap: KClass<V>): Boolean {
        TODO("Not yet implemented")
    }

    override fun <V : Any> exists(key: String, wrap: KClass<V>): Boolean {
        TODO("Not yet implemented")
    }
}
