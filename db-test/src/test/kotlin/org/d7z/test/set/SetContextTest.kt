package org.d7z.test.set

import org.d7z.test.testSet
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class SetContextTest {
    companion object {
        private const val TEST_KEY = "DATA"
    }

    @Test
    fun get() {
        testSet {
            assertTrue(get(TEST_KEY, String::class).isEmpty)
            getOrCreate(TEST_KEY, String::class) { "data" }
            assertTrue(get(TEST_KEY, String::class).isPresent)
            drop(TEST_KEY)
            assertTrue(get(TEST_KEY, String::class).isEmpty)
        }
    }

    @Test
    fun getOrCreate() {
        testSet {
            assertTrue(get(TEST_KEY, String::class).isEmpty)
            getOrCreate(TEST_KEY, String::class) { "data" }
            assertTrue(get(TEST_KEY, String::class).isPresent)
            drop(TEST_KEY)
            assertTrue(get(TEST_KEY, String::class).isEmpty)
        }
    }

    @Test
    fun drop() {
        testSet {
            assertTrue(get(TEST_KEY, String::class).isEmpty)
            getOrCreate(TEST_KEY, String::class) { "data" }
            assertTrue(get(TEST_KEY, String::class).isPresent)
            drop(TEST_KEY)
            assertTrue(get(TEST_KEY, String::class).isEmpty)
        }
    }

    @Test
    fun exists() {
        testSet {
            assertTrue(get(TEST_KEY, String::class).isEmpty)
            getOrCreate(TEST_KEY, String::class) { "data" }
            drop(TEST_KEY)
            assertFalse(exists(TEST_KEY))
        }
    }
}
