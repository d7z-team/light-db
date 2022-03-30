package org.d7z.test.set

import org.d7z.light.db.api.struct.LightSet
import org.d7z.test.testSet
import org.junit.jupiter.api.Assertions.* // ktlint-disable no-wildcard-imports
import org.junit.jupiter.api.Test

class LightSetTest {
    companion object {
        private const val DEFAULT_KEY = "test-key"
    }

    private fun testContext(key: String = DEFAULT_KEY, block: LightSet<InternalTest>.(LightSet<InternalTest>) -> Unit) =
        testSet {
            it.drop(key)
            it.getOrCreate(key, InternalTest::class) {
                InternalTest("init", -1)
            }.apply {
                block(this, this)
            }
            it.drop(key)
        }

    data class InternalTest(
        val name: String,
        val age: Int,
    )

    @Test
    fun getSize() = testContext {
        assertEquals(1, size)
        add(InternalTest("master", 12))
        assertEquals(2, size)
        add(InternalTest("master", 14))
        assertEquals(3, size)
        add(InternalTest("master", 12))
        assertEquals(3, size)
    }

    @Test
    fun clear() = testContext {
        add(InternalTest("master", 12))
        add(InternalTest("master", 13))
        add(InternalTest("master", 14))
        add(InternalTest("master", 15))
        assertEquals(5, size)
    }

    @Test
    fun add() = testContext {
        assertTrue(add(InternalTest("master", 12)))
        assertTrue(add(InternalTest("master", 13)))
        assertTrue(add(InternalTest("master", 14)))
        assertTrue(add(InternalTest("master", 15)))
        assertEquals(5, size)
        assertFalse(add(InternalTest("master", 12)))
        assertEquals(5, size)
    }

    @Test
    fun remove() = testContext {
        add(InternalTest("master", 12))
        add(InternalTest("master", 13))
        add(InternalTest("master", 14))
        add(InternalTest("master", 15))
        assertEquals(5, size)
        assertFalse(add(InternalTest("master", 12)))
        assertEquals(5, size)
        assertTrue(remove(InternalTest("master", 15)))
        assertFalse(remove(InternalTest("master", 315)))
        assertEquals(4, size)
    }

    @Test
    fun contains() = testContext {
        add(InternalTest("master", 12))
        add(InternalTest("master", 13))
        add(InternalTest("master", 14))
        add(InternalTest("master", 15))
        assertFalse(contains(InternalTest("no", 12)))
        assertTrue(contains(InternalTest("master", 15)))
    }

    @Test
    fun values() = testContext {
        val data = listOf(
            InternalTest("one", 12),
            InternalTest("two", 13),
            InternalTest("three", 14),
            InternalTest("three", 14)
        )
        data.forEach { add(it) }
        values().asSequence().toSet().containsAll(data.toSet())
    }
}
