package org.d7z.light.db.memory.internal.data.map

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MemMapValueTest {

    private fun testContext(block: MemMapValue<String, String>.() -> Unit) {
        MemMapValue("test-key", String::class, String::class).apply(block)
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
