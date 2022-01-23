package edgn.lightdb.jedis.internal.jedis

import edgn.lightdb.api.DestroyException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import redis.clients.jedis.Jedis
import java.util.concurrent.TimeUnit

internal class JedisMetaTest {

    companion object {
        private const val DEFAULT_KEY = "test-key"
    }

    private fun withContext(key: String = DEFAULT_KEY, block: JedisMeta.(Jedis) -> Unit) {
        val pool = DefaultJedisPool()
        pool.session {
            it.del(key)
            block(JedisMeta(key, pool), it)
            it.del(key)
        }
        pool.close()
    }

    @Test
    fun getTtl() = withContext {
        assertThrows(DestroyException::class.java) {
            println(ttl)
        }
        it.rpush(DEFAULT_KEY, "data")
        assertEquals(-1, ttl)
        it.expire(DEFAULT_KEY, 10)
        assertEquals(10, ttl)
    }

    @Test
    fun expired() = withContext {
        it.rpush(DEFAULT_KEY, "data")
        assertEquals(-1, ttl)
        expired(10, TimeUnit.MINUTES)
        assertEquals(10 * 60, ttl)
        expired(-1, TimeUnit.MINUTES)
        assertThrows(DestroyException::class.java) {
            println(ttl)
        }
    }

    @Test
    fun clearExpire() = withContext {
        it.rpush(DEFAULT_KEY, "data")
        assertEquals(-1, ttl)
        expired(10, TimeUnit.MINUTES)
        assertEquals(10 * 60, ttl)
        clearExpire()
        assertEquals(-1, ttl)
    }

    @Test
    fun checkAvailable() = withContext {
        assertThrows(DestroyException::class.java) {
            this.checkAvailable {
            }
        }
        it.rpush(DEFAULT_KEY, "data")
        assertThrows(RuntimeException::class.java) {
            this.checkAvailable {
                throw RuntimeException()
            }
        }
    }

    @Test
    fun checkOrDefault() = withContext {
        assertEquals(
            checkOrDefault("0") {
                "1"
            },
            "0"
        )
        it.rpush(DEFAULT_KEY, "data")
        assertEquals(
            checkOrDefault("0") {
                "1"
            },
            "1"
        )
    }
}
