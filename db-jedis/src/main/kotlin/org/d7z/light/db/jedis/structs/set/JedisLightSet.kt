package org.d7z.light.db.jedis.structs.set

import org.d7z.light.db.api.error.DestroyException
import org.d7z.light.db.api.struct.LightSet
import org.d7z.light.db.jedis.LightJedisPool
import org.d7z.objects.format.api.IDataCovert
import kotlin.reflect.KClass

class JedisLightSet<V : Any>(
    val name: String,
    override val valueType: KClass<V>,
    private val pool: LightJedisPool,
    private val dataCovert: IDataCovert,
) : LightSet<V> {
    override fun add(data: V): Boolean = pool.session {
        it.eval(
            """
             if redis.call('EXISTS',KEYS[1]) == 1 then
                return -1
             end
             return redis.call('SADD',KEYS[1],ARGV[1])   
            """.trimIndent(),
            1, name, dataCovert.format(data, valueType)
        ).apply {
            if (this == -1L) {
                throw DestroyException("key $name not exists.")
            }
        } != 0L
    }

    override fun remove(data: V): Boolean = pool.session {
        it.eval(
            """
             if redis.call('EXISTS',KEYS[1]) == 1 then
                return -1
             end
             return redis.call('SREM',KEYS[1],ARGV[1])   
            """.trimIndent(),
            1, name, dataCovert.format(data, valueType)
        ).apply {
            if (this == -1L) {
                throw DestroyException("key $name not exists.")
            }
        } != 0L
    }

    override fun contains(data: V): Boolean = pool.session {
        it.eval(
            """
             if redis.call('EXISTS',KEYS[1]) == 1 then
                return -1
             end
             return redis.call('SISMEMBER',KEYS[1],ARGV[1])   
            """.trimIndent(),
            1, name, dataCovert.format(data, valueType)
        ).apply {
            if (this == -1L) {
                throw DestroyException("key $name not exists.")
            }
        } != 0L
    }

    override fun values(): Iterator<V> = pool.session {
        it.sunion(name).apply {
            if (this.size == 0) {
                throw DestroyException("key $name not exists.")
            }
        }.map { item -> dataCovert.reduce(item, valueType) }.iterator()
    }

    override val size: Long
        get() = pool.session {
            it.scard(name).apply {
                if (this == 0L) {
                    throw DestroyException("key $name not exists.")
                }
            }
        }
}
