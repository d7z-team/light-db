package org.d7z.light.db.memory.internal.data.set

import org.d7z.light.db.api.utils.metaOrNull
import org.d7z.light.db.api.utils.metaOrThrows
import org.d7z.light.db.memory.MemoryMetaData
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

internal class MemSetGroupTest {

    private fun testContext(block: MemSetGroup.() -> Unit) {
        block(MemSetGroup())
    }

    companion object {
        private const val TEST_KEY = "DATA"
    }

    @Test
    fun get() {
        testContext {
            assertTrue(get(TEST_KEY, String::class).isEmpty)
            getOrCreate(TEST_KEY, String::class).add("data")
            assertTrue(get(TEST_KEY, String::class).isPresent)
            getOrCreate(TEST_KEY, String::class).clear()
            assertTrue(get(TEST_KEY, String::class).isEmpty)
            assertTrue(get(TEST_KEY, Int::class).isEmpty)
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
            assertTrue(get(TEST_KEY, Int::class).isEmpty)
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
            assertTrue(get(TEST_KEY, Int::class).isEmpty)
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
            assertTrue(get(TEST_KEY, Int::class).isEmpty)
            drop(TEST_KEY)
            assertFalse(exists(TEST_KEY))
        }
    }

    @Test
    fun clear() {
        testContext {
            getOrCreate(TEST_KEY, String::class).add("data")
            assertTrue(get(TEST_KEY, String::class).isPresent)
            clear()
            assertFalse(exists(TEST_KEY))
        }
    }

    @Test
    fun refresh() {
        testContext {
            getOrCreate(TEST_KEY, String::class)
            assertEquals(container.size, 1)
            getOrCreate(TEST_KEY, String::class)
                .metaOrThrows<MemoryMetaData, String>()
                .expired(-1, TimeUnit.SECONDS)
            refresh()
            assertEquals(container.size, 0)
            val list = getOrCreate(TEST_KEY, String::class)
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
