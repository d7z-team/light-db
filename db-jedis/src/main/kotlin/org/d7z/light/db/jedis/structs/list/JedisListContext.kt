package org.d7z.light.db.jedis.structs.list

import org.d7z.light.db.api.error.DestroyException
import org.d7z.light.db.api.struct.LightList
import org.d7z.light.db.api.struct.ListContext
import org.d7z.light.db.jedis.LightJedisPool
import org.d7z.objects.format.api.IDataCovert
import java.util.Optional
import kotlin.reflect.KClass

class JedisListContext(
    private val name: String,
    private val pool: LightJedisPool,
    private val dataCovert: IDataCovert,
) : ListContext {
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightList<V>> {
        return if (exists(key)) {
            Optional.of(JedisLightList("$name:$key", wrap, pool, dataCovert))
        } else {
            Optional.empty()
        }
    }

    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>, create: () -> V) = pool.session {
        it.eval(
            """
            if redis.call('EXISTS',KEYS[1]) == 1 then
                return 0
            else
                redis.call('RPUSH',KEYS[1],ARGV[1])
                return 1
            end
            """.trimIndent(),
            1, "$name:$key", dataCovert.format(create(), wrap)
        ) // 如果数据不存在则注入
        JedisLightList("$name:$key", wrap, pool, dataCovert)
    }

    override fun exists(key: String) = pool.session {
        it.exists("$name:$key")
    }

    override fun getTimeout(key: String) = pool.session {
        when (val expireTime = it.ttl("$name:$key")) {
            -2L -> {
                throw DestroyException("key '$key' not found.")
            }
            -1L -> {
                -1
            }
            else -> {
                expireTime
            }
        }
    }

    override fun setTimeout(key: String, second: Long) = pool.session {
        if (second < 0) {
            it.persist("$name:$key") == 1L
        } else {
            it.expire("$name:$key", second) == 1L
        }
    }
}
