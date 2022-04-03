package org.d7z.light.db.jedis

import org.d7z.light.db.api.LightDB
import org.d7z.light.db.api.struct.ListContext
import org.d7z.light.db.api.struct.MapContext
import org.d7z.light.db.api.struct.SetContext
import org.d7z.light.db.jedis.structs.list.JedisListContext
import org.d7z.light.db.jedis.structs.map.JedisMapContext
import org.d7z.light.db.jedis.structs.set.JedisSetContext
import org.d7z.objects.format.api.IDataCovert
import java.util.concurrent.ConcurrentHashMap

/**
 * LightDB Jedis Support
 */
class JedisLightDB @JvmOverloads constructor(
    header: String = "",
    private val pool: LightJedisPool = LightJedisPool,
    private val dataCovert: IDataCovert = IDataCovert,
    private val cache: Boolean = false,
) : LightDB {
    override val name = "LightDB Jedis Support"

    init {
        pool.session {
            it.exists("1")
        }
    }

    private val head = if (header.isBlank()) {
        ""
    } else {
        "$header:"
    }

    private val cachedMapGroup = ConcurrentHashMap<String, MapContext>()

    private val cachedSetGroup = ConcurrentHashMap<String, SetContext>()
    private val cachedListGroup = ConcurrentHashMap<String, ListContext>()

    override fun withList(name: String): ListContext {
        return if (cache) {
            cachedListGroup.getOrPut(name) {
                JedisListContext("${head}list:$name", pool, dataCovert)
            }
        } else {
            JedisListContext("${head}list:$name", pool, dataCovert)
        }
    }

    override fun withMap(name: String): MapContext {
        return if (cache) {
            cachedMapGroup.getOrPut(name) {
                JedisMapContext("${head}map:$name", pool, dataCovert)
            }
        } else {
            JedisMapContext("${head}map:$name", pool, dataCovert)
        }
    }

    override fun withSet(name: String): SetContext {
        return if (cache) {
            cachedSetGroup.getOrPut(name) {
                JedisSetContext("${head}set:$name", pool, dataCovert)
            }
        } else {
            JedisSetContext("${head}set:$name", pool, dataCovert)
        }
    }

    override fun close() {
        pool.close()
    }
}
