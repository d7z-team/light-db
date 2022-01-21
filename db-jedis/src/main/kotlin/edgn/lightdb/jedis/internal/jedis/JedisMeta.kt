package edgn.lightdb.jedis.internal.jedis

import edgn.lightdb.api.DestroyException
import edgn.lightdb.jedis.options.JedisMetaData
import edgn.lightdb.jedis.options.JedisPool
import redis.clients.jedis.Jedis
import java.util.Optional
import java.util.concurrent.TimeUnit

class JedisMeta(
    private val groupKey: String,
    private val pool: JedisPool
) : JedisMetaData {
    override val ttl: Long
        get() = checkAvailable {
            it.ttl(groupKey)
        }

    override fun expired(ttl: Long, unit: TimeUnit): Unit = checkAvailable {
        it.expire(groupKey, unit.toSeconds(ttl))
    }

    override fun clearExpire(): Unit = checkAvailable {
        it.expire(groupKey, -1)
    }

    fun <T : Any> checkAvailable(function: (Jedis) -> T): T = pool.session {
        if (it.exists(groupKey)) {
            function(it)
        } else {
            throw DestroyException("实例 \'$groupKey\' 不存在或已过期.")
        }
    }

    fun <T : Any> checkOrDefault(default: T, function: (Jedis) -> T): T = pool.session {
        if (it.exists(groupKey)) {
            function(it)
        } else {
            default
        }
    }

    fun <T : Any> session(function: (Jedis) -> T): T = pool.session(function)

    override fun get(key: String): Optional<String> {
        return when (key.lowercase()) {
            "db.ttl" -> {
                return Optional.of(ttl.toString())
            }
            else -> {
                Optional.empty()
            }
        }
    }

    override fun set(key: String, value: String): Boolean {
        when (key.lowercase()) {
            "db.ttl" -> {
                expired(
                    value.toLongOrNull() ?: throw NumberFormatException("无法格式化 $value."), TimeUnit.SECONDS
                )
            }
            else -> {
                return false
            }
        }
        return true
    }
}
