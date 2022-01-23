package edgn.lightdb.jedis.internal.data.map

import edgn.lightdb.jedis.internal.jedis.DefaultJedisPool
import edgn.lightdb.jedis.options.JedisLightDBConfig
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import redis.clients.jedis.Jedis

internal class JedisMapValueTest {

    companion object {
        private const val DEFAULT_KEY = "test-key"
        private val CONFIG = JedisLightDBConfig()
    }

    private fun testContext(key: String = DEFAULT_KEY, block: JedisMapValue<String, String>.(Jedis) -> Unit) {
        val pool = DefaultJedisPool()
        pool.session {
            it.del(key)
            block(JedisMapValue(pool, CONFIG, key, String::class, String::class), it)
            it.del(key)
        }
        pool.close()
    }

    @Test
    fun clear() = testContext {
        put("a", "b")
        put("b", "b")
        put("c", "b")
        put("d", "b")
        assertEquals(size, 4)
        clear()
        assertEquals(size, 0)
    }

    @Test
    fun put() = testContext {
        assertFalse(put("a", "b").isPresent)
        put("b", "b")
        put("c", "b")
        assertEquals(put("c", "d").get(), "b")
        assertEquals(size, 3)
    }

    @Test
    fun putIfAbsent() = testContext {
        put("a", "b")
        put("b", "b")
        put("c", "b")
        put("c", "b")
        assertEquals(size, 3)
        putIfAbsent("b", "d")
        assertEquals(get("b").get(), "b")
        putIfAbsent("d", "e")
        assertEquals(get("d").get(), "e")
    }

    @Test
    fun compareAndSwap() = testContext {
        put("a", "d")
        put("b", "c")
        put("c", "b")
        put("d", "a")
        assertTrue(compareAndSwap("a", "d", "b"))
        put("b", "e")
        assertFalse(compareAndSwap("b", "c", "z"))
        assertTrue(compareAndSwap("b", get("b").get(), "z"))
        assertTrue(compareAndSwap("e", "c", "z"))
    }

    @Test
    fun containsKey() = testContext {
        put("a" to "d")
        put("b", "c")
        put("c", "b")
        put("d", "a")
        assertTrue(containsKey("a"))
        assertFalse(containsKey("A"))
    }

    @Test
    fun get() = testContext {
        put("a", "d")
        put("b", "c")
        put("c", "b")
        put("d", "a")
        assertTrue(get("a").isPresent)
        assertFalse(get("e").isPresent)
    }

    @Test
    fun keys() = testContext {
        val data = mapOf(
            "a" to "d",
            "b" to "c",
            "c" to "b",
            "c" to "a",
        )
        data.forEach { (t, u) ->
            put(t, u)
        }
        assertEquals(data.keys, this.keys().asSequence().toSet())
    }

    @Test
    fun getSize() = testContext {
        put("a", "d")
        put("b", "c")
        put("c", "b")
        put("c", "a")
        assertEquals(size, 3)
    }

    @Test
    fun removeKey() = testContext {
        put("a", "d")
        put("b", "c")
        put("c", "b")
        put("c", "a")
        assertEquals(size, 3)
        assertEquals(removeKey("c").get(), "a")
        assertEquals(size, 2)
    }
}
