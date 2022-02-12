package org.d7z.light.db.memory.internal.data.list

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class MemListValueTest {

    private fun testContext(block: MemListValue<InternalTest>.() -> Unit) {
        MemListValue("test-key", InternalTest::class).apply(block)
    }

    data class InternalTest(
        val name: String,
        val age: Int
    )

    @Test
    fun getSize() {
        testContext {
            assertEquals(size, 0)
            add(InternalTest("one", 12))
            add(InternalTest("one", 12))
            add(InternalTest("one", 12))
            assertEquals(size, 3)
            clear()
            assertEquals(size, 0)
        }
    }

    @Test
    fun add() {
        testContext {
            assertEquals(size, 0)
            add(InternalTest("one", 12))
            add(InternalTest("two", 12))
            add(InternalTest("one", 12))
            assertEquals(size, 3)
            clear()
            assertEquals(size, 0)
        }
    }

    @Test
    fun remove() {
        testContext {
            val element = InternalTest("one", 0)
            add(element)
            assertEquals(size, 1)
            assertEquals(get(0).get(), element)
            assertTrue(get(1).isEmpty)
        }
    }

    @Test
    fun set() {
        testContext {
            add(InternalTest("one", 12))
            add(InternalTest("two", 13))
            add(InternalTest("three", 14))
            assertEquals(get(0).get().age, 12)
            set(0, InternalTest("one", 13))
            assertEquals(get(0).get().age, 13)
            assertThrows(IndexOutOfBoundsException::class.java) {
                set(3, InternalTest("four", 15))
            }
        }
    }

    @Test
    fun indexOf() {
        testContext {
            add(InternalTest("one", 12))
            add(InternalTest("two", 13))
            add(InternalTest("three", 14))
            add(InternalTest("one", 12))
            add(InternalTest("two", 13))
            assertEquals(indexOf(InternalTest("one", 12)), 0)
            assertEquals(indexOf(InternalTest("one", 13)), -1)
            assertEquals(indexOf(InternalTest("three", 14)), 2)
        }
    }

    @Test
    fun lastIndexOf() {
        testContext {
            add(InternalTest("one", 12))
            add(InternalTest("two", 13))
            add(InternalTest("three", 14))
            add(InternalTest("one", 12))
            add(InternalTest("two", 13))
            assertEquals(lastIndexOf(InternalTest("one", 12)), 3)
            assertEquals(lastIndexOf(InternalTest("one", 13)), -1)
            assertEquals(lastIndexOf(InternalTest("three", 14)), 2)
        }
    }

    @Test
    fun values() {
        testContext {
            val data = listOf(
                InternalTest("one", 12),
                InternalTest("two", 13),
                InternalTest("three", 14),
                InternalTest("three", 14)
            )
            data.forEach { add(it) }
            assertEquals(values().asSequence().toList(), data)
        }
    }
}
