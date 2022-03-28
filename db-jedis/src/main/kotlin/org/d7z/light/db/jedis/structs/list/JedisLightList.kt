package org.d7z.light.db.jedis.structs.list

import org.d7z.light.db.api.error.DestroyException
import org.d7z.light.db.api.struct.LightList
import org.d7z.light.db.jedis.LightJedisPool
import org.d7z.objects.format.api.IDataCovert
import java.util.Optional
import kotlin.reflect.KClass

class JedisLightList<V : Any>(
    val name: String,
    override val valueType: KClass<V>,
    private val pool: LightJedisPool,
    private val dataCovert: IDataCovert,
) : LightList<V> {
    override fun add(element: V): Unit = pool.session {
        it.eval(
            """
             if redis.call('EXISTS',KEYS[1]) == 1 then
                redis.call('RPUSH',KEYS[1],ARGV[1])
                return 1
             else
                return -1
             end
            """.trimIndent(),
            1, name, dataCovert.format(element, valueType)
        ).let { data ->
            if (data != 1L) {
                throw DestroyException("key $name not exists.")
            }
        }
    }

    override fun add(index: Long, element: V) = pool.session {
        val res = it.eval(
            """
            if redis.call('EXISTS',KEYS[1]) ~= 1 then
                return -2
            end
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
            name,
            index.toString(),
            dataCovert.format(element, valueType)
        ) as Long
        if (res == -1L) {
            throw IndexOutOfBoundsException("$name size ${it.llen(name)}  < $index")
        }
        if (res == -2L) {
            throw DestroyException("key $name not exists.")
        }
    }

    override fun remove(index: Long): Optional<V> = pool.session {
        it.eval(
            """
            if redis.call('EXISTS',KEYS[1]) ~= 1 then
                return 'LIGHT-DB-CHECK-NOT-FOUND'
            end
            if redis.call('llen' , KEYS[1]) < tonumber(ARGV[1]) then
               return 'LIGHT-DB-CHECK-VALUE-NOT-FOUND'
            end
            local internal_data = redis.call('lindex' , KEYS[1], ARGV[1])
            local internal_tag = '_light_db_internal_del_tag_' .. ARGV[1]
            redis.call('lset' , KEYS[1] ,ARGV[1], internal_tag)
            redis.call('lrem', KEYS[1], 0, internal_tag)
            return internal_data
            """.trimIndent(),
            1,
            name,
            index.toString()
        ).toString().run {
            if (this == "LIGHT-DB-CHECK-NOT-FOUND") {
                throw DestroyException("key $name not exists.")
            } else if (this == "LIGHT-DB-CHECK-VALUE-NOT-FOUND") {
                Optional.empty()
            } else {
                Optional.ofNullable(dataCovert.reduce(this, valueType))
            }
        }
    }

    override fun set(index: Long, element: V): V = pool.session {
        it.eval(
            """
            if redis.call('EXISTS',KEYS[1]) ~= 1 then
                return 'LIGHT-DB-CHECK-NOT-FOUND'
            end
            if redis.call('llen' , KEYS[1]) < tonumber(ARGV[1]) then
               return 'LIGHT-DB-CHECK-VALUE-NOT-FOUND'
            end
            local old_data = redis.call('lindex' , KEYS[1], ARGV[1])
            redis.call('lset' , KEYS[1] ,ARGV[1], ARGV[2])
            return old_data
            """.trimIndent(),
            1,
            name,
            index.toString(),
            dataCovert.format(element, valueType)
        ).toString().run {
            if (this == "LIGHT-DB-CHECK-NOT-FOUND") {
                throw DestroyException("key $name not exists.")
            } else if (this == "LIGHT-DB-CHECK-VALUE-NOT-FOUND") {
                throw IndexOutOfBoundsException("index: $index > max: ${it.llen(name) - 1} ")
            } else {
                dataCovert.reduce(this, valueType)
            }
        }
    }

    override fun get(index: Long): Optional<V> = pool.session {
        it.eval(
            """
            if redis.call('EXISTS',KEYS[1]) ~= 1 then
                return 'LIGHT-DB-CHECK-NOT-FOUND'
            end
            return redis.call('LINDEX',KEYS[1],tonumber(ARGV[1]))
            """.trimIndent(),
            1,
            name,
            index.toString()
        )?.toString()?.run {
            if (this == "LIGHT-DB-CHECK-NOT-FOUND") {
                throw DestroyException("key $name not exists.")
            } else {
                Optional.ofNullable(dataCovert.reduce(this, valueType))
            }
        } ?: Optional.empty()
    }

    override fun indexOf(element: V): Long = pool.session {
        (
            it.eval(
                """
            if redis.call('EXISTS',KEYS[1]) ~= 1 then
                return -2
            end
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
                name,
                dataCovert.format(element, valueType)
            ) as Long
            ).apply {
            if (this == -2L) {
                throw DestroyException("key $name not exists.")
            }
        }
    }

    override fun lastIndexOf(element: V): Long = pool.session { jedis ->
        (
            jedis.eval(
                """
            if redis.call('EXISTS',KEYS[1]) ~= 1 then
                return -2
            end
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
                name,
                dataCovert.format(element, valueType)
            ) as Long
            ).apply {
            if (this == -2L) {
                throw DestroyException("key $name not exists.")
            }
        }
    }

    override val size: Long
        get() = pool.session {
            it.llen(name).apply {
                if (this == 0L) {
                    throw DestroyException("key $name not exists.")
                }
            }
        }
}
