package org.d7z.light.db.memory.internal.data.set

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MemSetValueTest {
    private fun testContext(block: MemSetValue<InternalTest>.() -> Unit) {
        block(MemSetValue("test-key", InternalTest::class))
    }

    data class InternalTest(
        val name: String,
        val age: Int
    )

    @Test
    fun getSize() = testContext {
        assertEquals(0, size)
        add(InternalTest("master", 12))
        assertEquals(1, size)
        add(InternalTest("master", 14))
        assertEquals(2, size)
        add(InternalTest("master", 12))
        assertEquals(2, size)
    }

    @Test
    fun clear() = testContext {
        add(InternalTest("master", 12))
        add(InternalTest("master", 13))
        add(InternalTest("master", 14))
        add(InternalTest("master", 15))
        assertEquals(4, size)
        clear()
        assertEquals(0, size)
    }

    @Test
    fun add() = testContext {
        assertTrue(add(InternalTest("master", 12)))
        assertTrue(add(InternalTest("master", 13)))
        assertTrue(add(InternalTest("master", 14)))
        assertTrue(add(InternalTest("master", 15)))
        assertEquals(4, size)
        assertFalse(add(InternalTest("master", 12)))
        assertEquals(4, size)
    }

    @Test
    fun remove() = testContext {
        add(InternalTest("master", 12))
        add(InternalTest("master", 13))
        add(InternalTest("master", 14))
        add(InternalTest("master", 15))
        assertEquals(4, size)
        assertFalse(add(InternalTest("master", 12)))
        assertEquals(4, size)
        assertTrue(remove(InternalTest("master", 15)))
        assertFalse(remove(InternalTest("master", 315)))
        assertEquals(3, size)
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
        assertEquals(values().asSequence().toSet(), data.toSet())
    }
}
