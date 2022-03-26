package org.d7z.light.db.jedis

import org.d7z.light.db.api.LightDB
import org.d7z.light.db.api.struct.ListContext
import org.d7z.light.db.api.struct.MapContext
import org.d7z.light.db.api.struct.SetContext
import org.d7z.light.db.jedis.internal.data.list.JedisListContext
import org.d7z.light.db.jedis.internal.data.map.JedisMapContext
import org.d7z.light.db.jedis.internal.data.set.JedisSetContext
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
        ConcurrentHashMap<String, MapContext>()
    }

    private val cachedSetGroup by lazy {
        ConcurrentHashMap<String, SetContext>()
    }

    private val cachedListGroup by lazy {
        ConcurrentHashMap<String, ListContext>()
    }

    override val name = "LightDB Jedis Support"

    override fun withMap(name: String): MapContext {
        return if (config.cache) {
            cachedMapGroup.getOrPut(name) {
                JedisMapContext(name, pool, config)
            }
        } else {
            JedisMapContext(name, pool, config)
        }
    }

    override fun withList(name: String): ListContext {
        return if (config.cache) {
            cachedListGroup.getOrPut(name) {
                JedisListContext(name, pool, config)
            }
        } else {
            JedisListContext(name, pool, config)
        }
    }

    override fun withSet(name: String): SetContext {
        return if (config.cache) {
            cachedSetGroup.getOrPut(name) {
                JedisSetContext(name, pool, config)
            }
        } else {
            JedisSetContext(name, pool, config)
        }
    }

    override fun close() {
        pool.close()
    }
}
