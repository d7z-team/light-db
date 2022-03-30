package org.d7z.test.list

import org.d7z.light.db.api.struct.LightList
import org.d7z.test.testList
import org.junit.jupiter.api.Assertions.* // ktlint-disable no-wildcard-imports
import org.junit.jupiter.api.Test

class LightListTest {
    companion object {
        private const val DEFAULT_KEY = "test-key"
    }

    private fun testContext(
        key: String = DEFAULT_KEY,
        block: LightList<InternalTest>.(LightList<InternalTest>) -> Unit,
    ) = testList {
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
    fun getSize() {
        testContext {
            assertEquals(size, 1)
            add(InternalTest("one", 12))
            add(InternalTest("one", 12))
            add(InternalTest("one", 12))
            assertEquals(size, 4)
            remove(1)
            assertEquals(size, 3)
        }
    }

    @Test
    fun add() {
        testContext {
            assertEquals(size, 1)
            add(InternalTest("one", 12))
            add(InternalTest("two", 12))
            add(InternalTest("one", 12))
            assertEquals(size, 4)
        }
    }

    @Test
    fun remove() {
        testContext {
            val element = InternalTest("one", 0)
            add(element)
            assertEquals(size, 2)
            assertEquals(get(1).get(), element)
            assertTrue(get(2).isEmpty)
        }
    }

    @Test
    fun set() {
        testContext {
            add(InternalTest("one", 12))
            add(InternalTest("two", 13))
            add(InternalTest("three", 14))
            assertEquals(get(1).get().age, 12)
            set(1, InternalTest("one", 13))
            assertEquals(get(1).get().age, 13)
            assertThrows(IndexOutOfBoundsException::class.java) {
                set(4, InternalTest("four", 15))
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
            assertEquals(indexOf(InternalTest("one", 12)), 1)
            assertEquals(indexOf(InternalTest("one", 13)), -1)
            assertEquals(indexOf(InternalTest("three", 14)), 3)
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
            assertEquals(lastIndexOf(InternalTest("one", 12)), 4)
            assertEquals(lastIndexOf(InternalTest("one", 13)), -1)
            assertEquals(lastIndexOf(InternalTest("three", 14)), 3)
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
            assertTrue(values().asSequence().toList().containsAll(data))
        }
    }
}
