package org.d7z.test.map

import org.d7z.light.db.api.struct.LightMap
import org.d7z.test.testMap
import org.junit.jupiter.api.Assertions.* // ktlint-disable no-wildcard-imports
import org.junit.jupiter.api.Test

class LightMapTest {
    companion object {
        private const val DEFAULT_KEY = "test-key"
    }

    private fun testContext(
        key: String = DEFAULT_KEY,
        block: LightMap<String, String>.(LightMap<String, String>) -> Unit,
    ) = testMap {
        it.drop(key)
        it.getOrCreate(key, String::class, String::class) { "data init" to "data init" }
            .apply { block(this, this) }
        it.drop(key)
    }

    @Test
    fun clear() = testContext {
        put("a", "b")
        put("b", "b")
        put("c", "b")
        put("d", "b")
        assertEquals(size, 5)
    }

    @Test
    fun put() = testContext {
        assertFalse(put("a", "b").isPresent)
        put("b", "b")
        put("c", "b")
        assertEquals(put("c", "d").get(), "b")
        assertEquals(size, 4)
    }

    @Test
    fun putIfAbsent() = testContext {
        put("a", "b")
        put("b", "b")
        put("c", "b")
        put("c", "b")
        assertEquals(size, 4)
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
        this.keys().asSequence().toSet().containsAll(data.keys)
    }

    @Test
    fun getSize() = testContext {
        put("a", "d")
        put("b", "c")
        put("c", "b")
        put("c", "a")
        assertEquals(size, 4)
    }

    @Test
    fun removeKey() = testContext {
        put("a", "d")
        put("b", "c")
        put("c", "b")
        put("c", "a")
        assertEquals(size, 4)
        assertEquals(removeKey("c").get(), "a")
        assertEquals(size, 3)
    }
}
