package org.d7z.light.db.jedis

import org.d7z.light.db.api.LightDB
import org.d7z.light.db.api.struct.ListContext
import org.d7z.light.db.api.struct.MapContext
import org.d7z.light.db.api.struct.SetContext
import org.d7z.light.db.jedis.structs.list.JedisListContext
import org.d7z.light.db.jedis.structs.map.JedisMapContext
import org.d7z.objects.format.GlobalObjectFormat
import org.d7z.objects.format.api.IDataCovert
import java.util.concurrent.ConcurrentHashMap

/**
 * LightDB Jedis Support
 */
class JedisLightDB @JvmOverloads constructor(
    header: String = "",
    private val pool: LightJedisPool = LightJedisPool,
    private val dataCovert: IDataCovert = GlobalObjectFormat,
) : LightDB {
    override val name = "LightDB Jedis Support"

    private val head = if (header.isBlank()) {
        ""
    } else {
        "$header:"
    }

    private val cachedMapGroup = ConcurrentHashMap<String, MapContext>()

    private val cachedSetGroup = ConcurrentHashMap<String, SetContext>()
    private val cachedListGroup = ConcurrentHashMap<String, ListContext>()

    override fun withList(name: String): ListContext {
        return cachedListGroup.getOrPut(name) {
            JedisListContext("${head}list:$name", pool, dataCovert)
        }
    }

    override fun withMap(name: String): MapContext {
        return cachedMapGroup.getOrPut(name) {
            JedisMapContext("${head}map:$name", pool, dataCovert)
        }
    }

    override fun withSet(name: String): SetContext {
        return cachedSetGroup.getOrPut(name) {
            TODO()
        }
    }

    override fun close() {
        pool.close()
    }
}
