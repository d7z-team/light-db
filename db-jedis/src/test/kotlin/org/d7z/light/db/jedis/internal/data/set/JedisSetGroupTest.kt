package org.d7z.light.db.jedis.internal.data.set

import org.d7z.light.db.jedis.internal.jedis.DefaultJedisPool
import org.d7z.light.db.jedis.options.JedisLightDBConfig
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class JedisSetGroupTest {
    companion object {
        private const val TEST_KEY = "DATA"
    }

    private fun testContext(block: JedisSetGroup.() -> Unit) {

        val pool = DefaultJedisPool()
        pool.resource.run {
            del("set:test:$TEST_KEY")
            block(JedisSetGroup("test", pool, JedisLightDBConfig()))
            del("set:test:$TEST_KEY")
        }
    }

    @Test
    fun get() {
        testContext {
            assertTrue(get(TEST_KEY, String::class).isEmpty)
            getOrCreate(TEST_KEY, String::class).add("data")
            assertTrue(get(TEST_KEY, String::class).isPresent)
            getOrCreate(TEST_KEY, String::class).clear()
            assertTrue(get(TEST_KEY, String::class).isEmpty)
//            assertTrue(get(TEST_KEY, Int::class).isEmpty)
            drop(TEST_KEY)
            assertTrue(get(TEST_KEY, String::class).isEmpty)
        }
    }

    @Test
    fun getOrCreate() {
        testContext {
            assertTrue(get(TEST_KEY, String::class).isEmpty)
            getOrCreate(TEST_KEY, String::class).add("data")
            assertTrue(get(TEST_KEY, String::class).isPresent)
//            assertTrue(get(TEST_KEY, Int::class).isEmpty)
            drop(TEST_KEY)
            assertTrue(get(TEST_KEY, String::class).isEmpty)
        }
    }

    @Test
    fun drop() {
        testContext {
            assertTrue(get(TEST_KEY, String::class).isEmpty)
            getOrCreate(TEST_KEY, String::class).add("data")
            assertTrue(get(TEST_KEY, String::class).isPresent)
//            assertTrue(get(TEST_KEY, Int::class).isEmpty)
            drop(TEST_KEY)
            assertTrue(get(TEST_KEY, String::class).isEmpty)
        }
    }

    @Test
    fun exists() {
        testContext {
            assertTrue(get(TEST_KEY, String::class).isEmpty)
            getOrCreate(TEST_KEY, String::class).add("data")
            assertTrue(get(TEST_KEY, String::class).isPresent)
//            assertTrue(get(TEST_KEY, Int::class).isEmpty)
            drop(TEST_KEY)
            assertFalse(exists(TEST_KEY))
        }
    }
}
