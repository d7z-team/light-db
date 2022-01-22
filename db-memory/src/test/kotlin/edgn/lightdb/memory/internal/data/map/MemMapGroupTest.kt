package edgn.lightdb.memory.internal.data.map

import edgn.lightdb.api.utils.metaOrNull
import edgn.lightdb.api.utils.metaOrThrows
import edgn.lightdb.memory.MemoryMetaData
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

internal class MemMapGroupTest {

    private fun testContext(block: MemMapGroup.() -> Unit) {
        block(MemMapGroup())
    }

    @Test
    fun get() {
        testContext {
            assertTrue(get("DATA", String::class, String::class).isEmpty)
            getOrCreate("DATA", String::class, String::class)
            assertTrue(get("DATA", String::class, String::class).isPresent)
            assertTrue(get("DATA", Int::class, String::class).isEmpty)
            drop("DATA")
            assertTrue(get("DATA", String::class, String::class).isEmpty)
        }
    }

    @Test
    fun getOrCreate() {
        testContext {
            assertTrue(get("DATA", String::class, String::class).isEmpty)
            val data1 = getOrCreate("DATA", String::class, String::class)
            assertTrue(get("DATA", String::class, String::class).isPresent)
            val data2 = get("DATA", String::class, String::class).get()
            assertTrue(get("DATA", Int::class, String::class).isEmpty)
            assertEquals(data2, data1)
            drop("DATA")
            assertTrue(get("DATA", String::class, String::class).isEmpty)
        }
    }

    @Test
    fun drop() {
        testContext {
            assertTrue(get("DATA", String::class, String::class).isEmpty)
            getOrCreate("DATA", String::class, String::class)
            assertTrue(get("DATA", String::class, String::class).isPresent)
            assertTrue(get("DATA", Int::class, String::class).isEmpty)
            drop("DATA")
            assertTrue(get("DATA", String::class, String::class).isEmpty)
        }
    }

    @Test
    fun exists() {
        testContext {
            assertTrue(get("DATA", String::class, String::class).isEmpty)
            getOrCreate("DATA", String::class, String::class)
            assertTrue(get("DATA", String::class, String::class).isPresent)
            assertTrue(get("DATA", Int::class, String::class).isEmpty)
            drop("DATA")
            assertFalse(exists("DATA"))
        }
    }

    @Test
    fun clear() {
        testContext {
            getOrCreate("DATA", String::class, String::class)
            assertTrue(get("DATA", String::class, String::class).isPresent)
            clear()
            assertFalse(exists("DATA"))
        }
    }

    @Test
    fun refresh() {
        testContext {
            getOrCreate("DATA", String::class, String::class)
            assertEquals(container.size, 1)
            getOrCreate("DATA", String::class, String::class)
                .metaOrThrows<MemoryMetaData, String>()
                .expired(-1, TimeUnit.SECONDS)
            refresh()
            assertEquals(container.size, 0)
            val list = getOrCreate("DATA", String::class, String::class)
            assertEquals(container.size, 1)
            list.metaOrNull<MemoryMetaData, String>()
                .get()
                .expired(1, TimeUnit.SECONDS)
            Thread.sleep(2000)
            refresh()
            assertEquals(0, container.size)
        }
    }
}
