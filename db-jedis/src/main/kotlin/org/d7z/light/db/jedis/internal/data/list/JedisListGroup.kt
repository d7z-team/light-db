package org.d7z.light.db.jedis.internal.data.list

import org.d7z.light.db.api.structs.list.LightList
import org.d7z.light.db.api.structs.list.LightListGroup
import org.d7z.light.db.jedis.options.JedisLightDBConfig
import org.d7z.light.db.jedis.options.JedisPool
import java.util.Optional
import kotlin.reflect.KClass

class JedisListGroup(
    private val name: String,
    private val pool: JedisPool,
    private val config: JedisLightDBConfig
) : LightListGroup {
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightList<V>> = pool.session {
        val keyCover = keyCover(key)
        if (it.exists(keyCover).not()) {
            Optional.empty()
        } else {
            Optional.of(
                JedisListValue(
                    pool = pool,
                    config = config,
                    groupKey = keyCover,
                    valueType = wrap
                )
            )
        }
    }

    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>): LightList<V> = pool.session {
        JedisListValue(
            pool = pool,
            config = config,
            groupKey = keyCover(key),
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
        return "${config.redisHeader}list:$name:$key"
    }
}
