package org.d7z.light.db.jedis.internal.data.set

import org.d7z.light.db.api.struct.LightSet
import org.d7z.light.db.jedis.internal.jedis.JedisMeta
import org.d7z.light.db.jedis.options.JedisLightDBConfig
import org.d7z.light.db.jedis.options.JedisPool
import kotlin.reflect.KClass

class JedisSetValue<V : Any>(
    pool: JedisPool,
    config: JedisLightDBConfig,
    private val groupKey: String,
    override val valueType: KClass<V>
) : LightSet<V> {
    private val covert = config.dataCovert

    override val meta = JedisMeta(groupKey = groupKey, pool = pool)

    override val size: Long
        get() = meta.checkOrDefault(0) {
            it.scard(groupKey)
        }

    override fun clear(): Unit = meta.checkOrDefault(Unit) {
        it.multi().apply {
            del(groupKey)
        }.exec()
    }

    override fun add(data: V): Boolean = meta.session {
        it.sadd(groupKey, covert.format(data, valueType)) != 0L
    }

    override fun remove(data: V): Boolean = meta.checkOrDefault(false) {
        it.srem(groupKey, covert.format(data, valueType)) != 0L
    }

    override fun contains(data: V): Boolean = meta.checkOrDefault(false) {
        it.sismember(groupKey, covert.format(data, valueType))
    }

    override fun values(): Iterator<V> = meta.checkAvailable { jedis ->
        jedis.sunion(groupKey).map { covert.reduce(it, valueType) }.iterator()
    }
}
