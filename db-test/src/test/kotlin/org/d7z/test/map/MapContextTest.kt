package org.d7z.test.map

import org.d7z.test.testMap
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MapContextTest {

    companion object {
        private const val TEST_KEY = "DATA"
    }

    @Test
    fun get() {
        testMap {
            assertTrue(get(TEST_KEY, String::class, String::class).isEmpty)
            getOrCreate(TEST_KEY, String::class, String::class) { "data" to "data" }
            assertTrue(get(TEST_KEY, String::class, String::class).isPresent)
            drop(TEST_KEY)
            assertTrue(get(TEST_KEY, String::class, String::class).isEmpty)
        }
    }

    @Test
    fun getOrCreate() {
        testMap {
            assertTrue(get(TEST_KEY, String::class, String::class).isEmpty)
            getOrCreate(TEST_KEY, String::class, String::class) { "data" to "data" }
            assertTrue(get(TEST_KEY, String::class, String::class).isPresent)
            drop(TEST_KEY)
            assertTrue(get(TEST_KEY, String::class, String::class).isEmpty)
        }
    }

    @Test
    fun drop() {
        testMap {
            assertTrue(get(TEST_KEY, String::class, String::class).isEmpty)
            getOrCreate(TEST_KEY, String::class, String::class) { "data" to "data" }
            assertTrue(get(TEST_KEY, String::class, String::class).isPresent)
            drop(TEST_KEY)
            assertTrue(get(TEST_KEY, String::class, String::class).isEmpty)
        }
    }

    @Test
    fun exists() {
        testMap {
            assertTrue(get(TEST_KEY, String::class, String::class).isEmpty)
            getOrCreate(TEST_KEY, String::class, String::class) { "data" to "data" }
            assertTrue(get(TEST_KEY, String::class, String::class).isPresent)
            drop(TEST_KEY)
            assertFalse(exists(TEST_KEY))
        }
    }
}
