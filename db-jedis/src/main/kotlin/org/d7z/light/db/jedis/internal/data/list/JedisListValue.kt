package org.d7z.light.db.jedis.internal.data.list

import org.d7z.light.db.api.structs.list.LightList
import org.d7z.light.db.jedis.internal.jedis.JedisMeta
import org.d7z.light.db.jedis.options.JedisLightDBConfig
import org.d7z.light.db.jedis.options.JedisPool
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
        get() = meta.checkOrDefault(0) {
            it.llen(groupKey)
        }

    override fun clear(): Unit = meta.checkOrDefault(Unit) {
        it.del(groupKey)
    }

    override fun add(element: V): Unit = meta.session {
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

    override fun remove(index: Long): Optional<V> = meta.checkOrDefault(Optional.empty()) { jedis ->
        Optional.ofNullable(
            jedis.eval(
                """
            if redis.call('llen' , KEYS[1]) < tonumber(ARGV[1]) then
               return nil
            end
            local internal_data = redis.call('lindex' , KEYS[1], ARGV[1])
            local internal_tag = '_light_db_internal_del_tag_' .. ARGV[1]
            redis.call('lset' , KEYS[1] ,ARGV[1], internal_tag)
            redis.call('lrem', KEYS[1], 0, internal_tag)
            return internal_data
                """.trimIndent(),
                1,
                groupKey,
                index.toString()
            )
        ).map {
            covert.reduce(it as String, valueType)
        }
    }

    override fun set(index: Long, element: V): V = meta.checkAvailable { jedis ->
        Optional.ofNullable(
            jedis.eval(
                """
            if redis.call('llen' , KEYS[1]) <= tonumber(ARGV[1]) then
               return nil
            end
            local old_data = redis.call('lindex' , KEYS[1], ARGV[1])
            redis.call('lset' , KEYS[1] ,ARGV[1], ARGV[2])
            return old_data
                """.trimIndent(),
                1,
                groupKey,
                index.toString(),
                covert.format(element, valueType)
            ) as String?
        ).map {
            covert.reduce(it, valueType)
        }.orElseThrow {
            IndexOutOfBoundsException("index: $index > max: ${jedis.llen(groupKey) - 1} ")
        }
    }

    override fun get(index: Long): Optional<V> = meta.checkOrDefault(Optional.empty()) { jedis ->
        try {
            Optional.ofNullable(covert.reduce(jedis.lindex(groupKey, index), valueType))
        } catch (e: NullPointerException) {
            // 未找到数据
            Optional.empty()
        }
    }

    fun testData(): Optional<String> = meta.checkOrDefault(Optional.empty()) { jedis ->
        try {
            Optional.ofNullable(jedis.lindex(groupKey, 0))
        } catch (e: NullPointerException) {
            // 未找到数据
            Optional.empty()
        }
    }

    override fun indexOf(element: V): Long = meta.checkAvailable { jedis ->
        jedis.eval(
            """
            local key = KEYS[1]
            local obj = ARGV[1]
            local items = redis.call('lrange', key, 0, -1)
            for i =1,#items do
                if items[i] == obj then
                    return i - 1
                end
            end 
            return -1
            """.trimIndent(),
            1,
            groupKey,
            covert.format(element, valueType)
        ) as Long
    }

    override fun lastIndexOf(element: V): Long = meta.checkAvailable { jedis ->
        jedis.eval(
            """
            local key = KEYS[1]
            local obj = ARGV[1]
            local items = redis.call('lrange', key, 0, -1)
            for i =1,#items do
                if table.remove(items) == obj then
                    return #items 
                end
            end 
            return -1
            """.trimIndent(),
            1,
            groupKey,
            covert.format(element, valueType)
        ) as Long
    }

    override fun values(): Iterator<V> = meta.checkAvailable { jedis ->
        jedis.lrange(groupKey, 0, -1).map {
            covert.reduce(it, valueType)
        }.toList().iterator()
    }
}
