package edgn.lightdb.jedis.internal.data.list

import edgn.lightdb.api.structs.list.LightList
import edgn.lightdb.jedis.internal.jedis.JedisMeta
import edgn.lightdb.jedis.options.JedisLightDBConfig
import edgn.lightdb.jedis.options.JedisPool
import java.util.Optional
import kotlin.reflect.KClass

class JedisListValue<V : Any>(
    pool: JedisPool,
    config: JedisLightDBConfig,
    private val groupKey: String,
    override val valueType: KClass<V>,
) : LightList<V> {
    private val covert = config.dataCovert

    override val meta = JedisMeta(groupKey = groupKey, pool = pool)

    override val size: Long
        get() = meta.checkAvailable {
            it.llen(groupKey)
        }

    override fun clear(): Unit = meta.checkAvailable {
        it.multi().apply {
            del(groupKey)
            lpush(groupKey, "CREATE")
            lrem(groupKey, 0, "CREATE")
        }.exec()
    }

    override fun add(element: V): Unit = meta.checkAvailable {
        it.rpush(groupKey, covert.format(element, valueType))
    }

    override fun add(index: Long, element: V) = meta.checkAvailable {
        val res = it.eval(
            """
            if redis.call('llen' , KEYS[1]) < tonumber(ARGV[1]) then
               return -1
            end
            local internal_data = redis.call('lindex' , KEYS[1], ARGV[1])
            local internal_tag = '_light_db_internal_tag' .. ARGV[1]
            redis.call('lset' , KEYS[1] ,ARGV[1], internal_tag)
            redis.call('linsert', KEYS[1], 'AFTER', internal_tag , ARGV[2])
            redis.call('linsert', KEYS[1], 'AFTER',internal_tag, internal_data)
            redis.call('lrem', KEYS[1], 0, internal_tag)
            return 0
            """.trimIndent(),
            1,
            groupKey,
            index.toString(),
            covert.format(element, valueType)
        ) as Long
        if (res == -1L) {
            throw IndexOutOfBoundsException()
        }
    }

    override fun remove(index: Long): Optional<V> = meta.checkAvailable { jedis ->
        Optional.ofNullable(
            jedis.eval(
                """
            if redis.call('llen' , KEYS[1]) < tonumber(ARGV[1]) then
               return nil
            end
            local internal_data = redis.call('lindex' , KEYS[1], ARGV[1])
            local internal_tag = '_light_db_internal_del_tag' .. ARGV[1]
            redis.call('lset' , KEYS[1] ,ARGV[1], internal_tag)
            redis.call('lrem', KEYS[1], 0, internal_tag)
            return internal_data
                """.trimIndent(),
                1, "list", "1"
            )
        ).map {
            covert.reduce(it as String, valueType)
        }
    }

    override fun set(index: Long, element: V): V {
        TODO("Not yet implemented")
    }

    override fun get(index: Long): Optional<V> {
        TODO("Not yet implemented")
    }

    override fun indexOf(element: V): Long {
        TODO("Not yet implemented")
    }

    override fun lastIndexOf(element: V): Long {
        TODO("Not yet implemented")
    }

    override fun values(): Iterator<V> {
        TODO("Not yet implemented")
    }
}
