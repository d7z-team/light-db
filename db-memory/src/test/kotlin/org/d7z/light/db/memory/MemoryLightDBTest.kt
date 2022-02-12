package org.d7z.light.db.memory

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MemoryLightDBTest {

    private fun withContext(block: MemoryLightDB.() -> Unit) {
        block(MemoryLightDB())
    }

    @Test
    fun withList() = withContext {
        val def = withList()
        val named = withList("named")
        named.drop("user")
        assertNotEquals(named, def)
        named.getOrCreate("user", String::class).apply {
            add("init")
            assertEquals(get(0).get(), "init")
            remove(0)
            assertTrue(named.get("user", String::class).isEmpty)
        }
        named.drop("user")
    }

    @Test
    fun withMap() = withContext {
        val def = withMap()
        val named = withMap("named")
        named.drop("user")
        assertNotEquals(named, def)
        named.getOrCreate("user", String::class, String::class).apply {
            put("init", "a")
            assertEquals(get("init").get(), "a")
            removeKey("init")
            assertTrue(named.get("user", String::class, String::class).isEmpty)
        }
        named.drop("user")
    }

    @Test
    fun withSet() = withContext {
        val def = withSet()
        val named = withSet("named")
        named.drop("user")
        assertNotEquals(named, def)
        named.getOrCreate("user", String::class).apply {
            assertTrue(add("init"))
            assertFalse(add("init"))
            assertTrue(remove("init"))
            assertFalse(remove("init"))
            assertTrue(add("init"))
        }
        named.drop("user")
    }
}
