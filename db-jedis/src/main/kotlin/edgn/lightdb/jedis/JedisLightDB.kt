package edgn.lightdb.jedis

import edgn.lightdb.api.LightDB
import edgn.lightdb.api.structs.list.LightListGroup
import edgn.lightdb.api.structs.map.LightMapGroup
import edgn.lightdb.api.structs.set.LightSetGroup
import edgn.lightdb.jedis.internal.data.map.JedisMapGroup
import edgn.lightdb.jedis.internal.jedis.DefaultJedisPool
import edgn.lightdb.jedis.options.JedisLightDBConfig
import edgn.lightdb.jedis.options.JedisPool
import java.util.concurrent.ConcurrentHashMap

/**
 * LightDB Jedis Support
 */
class JedisLightDB @JvmOverloads constructor(
    private val pool: JedisPool = DefaultJedisPool(),
    private val config: JedisLightDBConfig = JedisLightDBConfig()
) : LightDB {

    private val cachedMapGroup by lazy {
        ConcurrentHashMap<String, LightMapGroup>()
    }

    private val cachedSetGroup by lazy {
        ConcurrentHashMap<String, LightSetGroup>()
    }

    private val cachedListGroup by lazy {
        ConcurrentHashMap<String, LightListGroup>()
    }

    override val name = "LightDB Jedis Support"

    override fun withMap(name: String): LightMapGroup {
        return if (config.cache) {
            cachedMapGroup.getOrPut(name) {
                JedisMapGroup(name, pool, config)
            }
        } else {
            JedisMapGroup(name, pool, config)
        }
    }

    override fun withList(name: String): LightListGroup {
        TODO("Not yet implemented")
    }

    override fun withSet(name: String): LightSetGroup {
        TODO("Not yet implemented")
    }

    override fun close() {
        pool.close()
    }
}
