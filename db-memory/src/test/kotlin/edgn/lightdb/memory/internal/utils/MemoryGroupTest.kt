package edgn.lightdb.memory.internal.utils

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class MemoryGroupTest {

    data class TestDataModules(
        override var available: Boolean,
        val name: String = ""
    ) : IDataModules {
        override val isNotEmpty = true
        override fun clear() {
        }
    }

    private fun testContext(block: MemoryGroup<TestDataModules>.() -> Unit) {
        block(MemoryGroup())
    }

    @Test
    fun get() = testContext {
        assertTrue(get("test").isEmpty)
        getOrCreate("test") {
            TestDataModules(true)
        }
        assertTrue(get("test").isPresent)
    }

    @Test
    fun remove() = testContext {
        assertTrue(get("test").isEmpty)
        getOrCreate("test") {
            TestDataModules(true)
        }
        remove("test")
        assertTrue(get("test").isEmpty)
    }

    @Test
    fun exists() = testContext {
        assertTrue(get("test").isEmpty)
        getOrCreate("test") {
            TestDataModules(true)
        }
        remove("test")
        assertFalse(exists("test"))
    }

    @Test
    fun removeIf() = testContext {
        listOf("testA", "testB", "testC", "deleteA", "testD").forEach {
            getOrCreate(it) {
                TestDataModules(true, it)
            }
        }
        removeIf { it.name.startsWith("delete") }
        assertTrue(exists("testA"))
        assertTrue(exists("testB"))
        assertTrue(exists("testC"))
        assertTrue(exists("testD"))
        assertFalse(exists("deleteA"))
    }

    @Test
    fun clear() = testContext {
        assertTrue(get("test").isEmpty)
        getOrCreate("test") {
            TestDataModules(true)
        }
        assertTrue(exists("test"))
        getOrCreate("test") {
            throw Exception()
        }.available = false
        clear()
        assertFalse(exists("test"))
    }
}
