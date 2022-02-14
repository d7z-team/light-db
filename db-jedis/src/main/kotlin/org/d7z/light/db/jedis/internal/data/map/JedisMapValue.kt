package org.d7z.light.db.jedis.internal.data.map

import org.d7z.light.db.api.structs.map.LightMap
import org.d7z.light.db.jedis.internal.jedis.JedisMeta
import org.d7z.light.db.jedis.options.JedisLightDBConfig
import org.d7z.light.db.jedis.options.JedisPool
import java.util.Optional
import kotlin.reflect.KClass

class JedisMapValue<K : Any, V : Any>(
    pool: JedisPool,
    config: JedisLightDBConfig,
    private val groupKey: String,
    override val keyType: KClass<K>,
    override val valueType: KClass<V>,
) : LightMap<K, V> {

    override val meta = JedisMeta(groupKey, pool)

    private val covert = config.dataCovert

    override fun clear(): Unit = meta.checkOrDefault(Unit) {
        it.multi().apply {
            del(groupKey)
        }.exec()
    }

    // 此 put 返回的数据不一定是上次的数据
    override fun put(key: K, value: V) = meta.session {
        val multi = it.multi()
        val nativeKey = covert.format(key, keyType)
        val last = multi.hget(groupKey, nativeKey)
        multi.hset(groupKey, nativeKey, covert.format(value, valueType))
        multi.exec()
        if (last.get() != null) {
            Optional.of(covert.reduce(last.get(), valueType))
        } else {
            Optional.empty<V>()
        }
    }

    override fun putIfAbsent(key: K, value: V) = meta.session {
        val multi = it.multi()
        val nativeKey = covert.format(key, keyType)
        val putRes = multi.hsetnx(groupKey, nativeKey, covert.format(value, valueType))
        val last = multi.hget(groupKey, nativeKey)
        multi.exec()
        if (putRes.get() == 1L) {
            covert.reduce(last.get(), valueType)
        } else {
            value
        }
    }

    /**
     *  使用 redis lua 脚本完成 CAS
     */
    override fun compareAndSwap(key: K, oldValue: V, newValue: V) = meta.checkAvailable {
        val nativeKey = covert.format(key, keyType)
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
            groupKey,
            nativeKey,
            covert.format(oldValue, valueType),
            covert.format(newValue, valueType)
        ) as Long
        result == 1L
    }

    override fun containsKey(key: K) = meta.checkAvailable {
        val nativeKey = covert.format(key, keyType)
        it.hexists(groupKey, nativeKey)
    }

    override fun get(key: K): Optional<V> = meta.checkAvailable { jedis ->
        val nativeKey = covert.format(key, keyType)
        Optional.ofNullable(jedis.hget(groupKey, nativeKey)).map { covert.reduce(it, valueType) }
    }

    override fun removeKey(key: K): Optional<V> = meta.checkAvailable { jedis ->
        val nativeKey = covert.format(key, keyType)
        val multi = jedis.multi()
        val res = multi.hget(groupKey, nativeKey)
        multi.hdel(groupKey, nativeKey)
        multi.exec()
        Optional.ofNullable(res.get()).map { item -> covert.reduce(item, valueType) }
    }

    /**
     * 在海量数据的情况下将导致程序崩溃
     */
    override fun keys() = meta.checkAvailable { jedis ->
        jedis.hkeys(groupKey).map { covert.reduce(it, keyType) }.iterator()
    }

    override val size: Long
        get() = meta.checkOrDefault(0) {
            it.hlen(groupKey)
        }
}
