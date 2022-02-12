package org.d7z.light.db.jedis

import org.d7z.light.db.api.LightDB
import org.d7z.light.db.api.structs.list.LightListGroup
import org.d7z.light.db.api.structs.map.LightMapGroup
import org.d7z.light.db.api.structs.set.LightSetGroup
import org.d7z.light.db.jedis.internal.data.list.JedisListGroup
import org.d7z.light.db.jedis.internal.data.map.JedisMapGroup
import org.d7z.light.db.jedis.internal.data.set.JedisSetGroup
import org.d7z.light.db.jedis.internal.jedis.DefaultJedisPool
import org.d7z.light.db.jedis.options.JedisLightDBConfig
import org.d7z.light.db.jedis.options.JedisPool
import java.util.concurrent.ConcurrentHashMap

/**
 * LightDB Jedis Support
 */
class JedisLightDB @JvmOverloads constructor(
    private val pool: JedisPool = DefaultJedisPool(),
    private val config: JedisLightDBConfig = JedisLightDBConfig(),
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
        return if (config.cache) {
            cachedListGroup.getOrPut(name) {
                JedisListGroup(name, pool, config)
            }
        } else {
            JedisListGroup(name, pool, config)
        }
    }

    override fun withSet(name: String): LightSetGroup {
        return if (config.cache) {
            cachedSetGroup.getOrPut(name) {
                JedisSetGroup(name, pool, config)
            }
        } else {
            JedisSetGroup(name, pool, config)
        }
    }

    override fun close() {
        pool.close()
    }
}
