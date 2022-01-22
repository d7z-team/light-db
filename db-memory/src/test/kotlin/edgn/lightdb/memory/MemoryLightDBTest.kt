package edgn.lightdb.memory

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.Optional

internal class MemoryLightDBTest {

    private fun withContext(block: MemoryLightDB.() -> Unit) {
        block(MemoryLightDB())
    }

    @Test
    fun withList() = withContext {
        val def = withList()
        val named = withList("named")
        assertNotEquals(named, def)
        named.getOrCreate("user", String::class).apply {
            add("init")
            assertEquals(get(0).get(), "init")
            remove(0)
            assertEquals(get(0), Optional.empty<String>())
        }
    }

    @Test
    fun withMap() = withContext {
        val def = withMap()
        val named = withMap("named")
        assertNotEquals(named, def)
        named.getOrCreate("user", String::class, String::class).apply {
            put("init", "a")
            assertEquals(get("init").get(), "init")
            removeKey("init")
            assertEquals(get("init"), Optional.empty<String>())
        }
    }

    @Test
    fun withSet() = withContext {
        val def = withSet()
        val named = withSet("named")
        assertNotEquals(named, def)
        named.getOrCreate("user", String::class).apply {
            assertTrue(add("init"))
            assertFalse(add("init"))
            assertTrue(remove("init"))
            assertFalse(remove("init"))
            assertTrue(add("init"))
        }
    }
}
