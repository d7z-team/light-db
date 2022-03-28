package org.d7z.light.db.jedis.structs.map

import org.d7z.light.db.api.error.DestroyException
import org.d7z.light.db.api.struct.LightMap
import org.d7z.light.db.jedis.LightJedisPool
import org.d7z.objects.format.api.IDataCovert
import java.util.Optional
import kotlin.reflect.KClass

class JedisLightMap<K : Any, V : Any>(
    val name: String,
    override val keyType: KClass<K>,
    override val valueType: KClass<V>,
    private val pool: LightJedisPool,
    private val dataCovert: IDataCovert,
) : LightMap<K, V> {
    override fun put(key: K, value: V): Optional<V> = pool.session {
        it.eval(
            """
            if redis.call('EXISTS',KEYS[1]) ~= 1 then
                return 'LIGHT-DB-CHECK-NOT-FOUND'
            end
            local old_data = redis.call('HGET',KEYS[1],ARGV[1])
            redis.call('HSET',KEYS[1],ARGV[1],ARGV[2])
            if old_data == true then
                return old_data
            else
                return 'LIGHT-DB-CHECK-VALUE-NOT-FOUND'
            end
            """.trimIndent(),
            1, name,
            dataCovert.format(key, keyType),
            dataCovert.format(value, valueType)
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

    override fun putIfAbsent(key: K, value: V): V = pool.session {
        it.eval(
            """
            if redis.call('EXISTS',KEYS[1]) ~= 1 then
                return 'LIGHT-DB-CHECK-NOT-FOUND'
            end
            old_data = redis.call('HGET',KEYS[1],ARGV[1])
           if old_data == false then
                redis.call('HSET',KEYS[1],ARGV[1],ARGV[2])
                return 'LIGHT-DB-CHECK-VALUE-FOUND'
            end
            return old_data
            """.trimIndent(),
            1, name,
            dataCovert.format(key, keyType),
            dataCovert.format(value, valueType)
        ).toString().run {
            if (this == "LIGHT-DB-CHECK-NOT-FOUND") {
                throw DestroyException("key $name not exists.")
            } else if (this == "LIGHT-DB-CHECK-VALUE-FOUND") {
                value
            } else {
                dataCovert.reduce(this, valueType)
            }
        }
    }

    override fun compareAndSwap(key: K, oldValue: V, newValue: V): Boolean = pool.session {
        val nativeKey = dataCovert.format(key, keyType)
        val result = it.eval(
            """
            local old_data = redis.call('hget' , KEYS[1], KEYS[2])
            if ARGV[1] == old_data or old_data == false  then
                redis.call('hset' , KEYS[1], KEYS[2], ARGV[2])
                return 1                
            else
                return 0
            end
            """.trimIndent(),
            2,
            name,
            nativeKey,
            dataCovert.format(oldValue, valueType),
            dataCovert.format(newValue, valueType)
        ) as Long
        result == 1L
    }

    override fun containsKey(key: K): Boolean = pool.session {
        it.eval(
            """
             if redis.call('EXISTS',KEYS[1]) ~= 1 then
                return -1
             end
             return redis.call('hexists',KEYS[1],ARGV[1])
            """.trimIndent(),
            1, name, dataCovert.format(key, valueType)
        ).let { data ->
            if (data == -1L) {
                throw DestroyException("key $name not exists.")
            }
            data == 1L
        }
    }

    override fun get(key: K): Optional<V> = pool.session {
        it.eval(
            """
             if redis.call('EXISTS',KEYS[1]) ~= 1 then
                return 'LIGHT-DB-CHECK-NOT-FOUND'
             end
             local map_data = redis.call('hget',KEYS[1],ARGV[1])
            if map_data == false then
                return 'LIGHT-DB-CHECK-VALUE-NOT-FOUND'
            end
            return map_data
            """.trimIndent(),
            1, name, dataCovert.format(key, keyType)
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

    override fun removeKey(key: K): Optional<V> = pool.session {
        it.eval(
            """
             if redis.call('EXISTS',KEYS[1]) ~= 1 then
                return 'LIGHT-DB-CHECK-NOT-FOUND'
             end
             local map_data = redis.call('hget',KEYS[1],ARGV[1])
            if map_data == false then
                return 'LIGHT-DB-CHECK-VALUE-NOT-FOUND'
            end
            redis.call('hdel',KEYS[1],ARGV[1])
            return map_data
            """.trimIndent(),
            1, name, dataCovert.format(key, keyType)
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

    override fun keys(): Iterator<K> = pool.session {
        it.hkeys(name).apply {
            if (this.size == 0) {
                throw DestroyException("key $name not exists.")
            }
        }.map { item -> dataCovert.reduce(item, keyType) }.iterator()
    }

    override val size: Long
        get() = pool.session {
            it.hlen(name).apply {
                if (this == 0L) {
                    throw DestroyException("key $name not exists.")
                }
            }
        }
}
