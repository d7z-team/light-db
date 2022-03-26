package org.d7z.light.db.jedis.internal.data.set

import org.d7z.light.db.api.struct.LightSet
import org.d7z.light.db.api.struct.SetContext
import org.d7z.light.db.jedis.options.JedisLightDBConfig
import org.d7z.light.db.jedis.options.JedisPool
import java.util.Optional
import kotlin.reflect.KClass

class JedisSetContext(
    private val name: String,
    private val pool: JedisPool,
    private val config: JedisLightDBConfig
) : SetContext {
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightSet<V>> = pool.session {
        val keyCover = keyCover(key)
        if (it.exists(keyCover).not()) {
            Optional.empty()
        } else {
            Optional.of(
                JedisSetValue(
                    pool = pool,
                    config = config,
                    groupKey = keyCover,
                    valueType = wrap
                )
            )
        }
    }

    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightSet<V> = pool.session {
        val keyCover = keyCover(key)
        JedisSetValue(
            pool = pool,
            config = config,
            groupKey = keyCover,
            valueType = wrap
        )
    }

    override fun drop(key: String): Boolean = pool.session {
        it.del(keyCover(key)) > 0
    }

    override fun exists(key: String): Boolean = pool.session {
        it.exists(keyCover(key))
    }

    private fun keyCover(key: String): String {
        return "${config.redisHeader}set:$name:$key"
    }
}
