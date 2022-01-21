package edgn.lightdb.jedis.internal.data.set

import edgn.lightdb.api.structs.set.LightSet
import edgn.lightdb.jedis.internal.jedis.JedisMeta
import edgn.lightdb.jedis.options.JedisLightDBConfig
import edgn.lightdb.jedis.options.JedisPool
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
        get() = meta.checkAvailable {
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
