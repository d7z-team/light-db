package org.d7z.test.list

import org.d7z.test.testList
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ListContextTest {
    companion object {
        private const val TEST_KEY = "DATA"
    }

    @Test
    fun get() {
        testList {
            this.drop(TEST_KEY)
            assertTrue(get(TEST_KEY, String::class).isEmpty)
            getOrCreate(TEST_KEY, String::class) { "data" }.apply {
                assertTrue(get(TEST_KEY, String::class).isPresent)
                remove(0)
            }
            assertTrue(get(TEST_KEY, String::class).isEmpty)
            drop(TEST_KEY)
            assertTrue(get(TEST_KEY, String::class).isEmpty)
        }
    }

    @Test
    fun getOrCreate() {
        testList {
            this.drop(TEST_KEY)
            assertTrue(get(TEST_KEY, String::class).isEmpty)
            getOrCreate(TEST_KEY, String::class) { "data" }
            assertTrue(get(TEST_KEY, String::class).isPresent)
            drop(TEST_KEY)
            assertTrue(get(TEST_KEY, String::class).isEmpty)
        }
    }

    @Test
    fun drop() {
        testList {
            this.drop(TEST_KEY)
            assertTrue(get(TEST_KEY, String::class).isEmpty)
            getOrCreate(TEST_KEY, String::class) { "data" }
            assertTrue(get(TEST_KEY, String::class).isPresent)
            drop(TEST_KEY)
            assertTrue(get(TEST_KEY, String::class).isEmpty)
        }
    }

    @Test
    fun exists() {
        testList {
            this.drop(TEST_KEY)
            assertTrue(get(TEST_KEY, String::class).isEmpty)
            getOrCreate(TEST_KEY, String::class) { "data" }
            assertTrue(get(TEST_KEY, String::class).isPresent)
            drop(TEST_KEY)
            assertFalse(exists(TEST_KEY))
        }
    }
}
