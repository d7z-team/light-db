package edgn.lightdb.memory.internal.data.list

import edgn.lightdb.memory.MemoryDataConfig
import org.junit.jupiter.api.Assertions.* // ktlint-disable no-wildcard-imports
import org.junit.jupiter.api.Test
import java.util.Optional

internal class MemListOptionTest {

    data class TestDataA(
        val name: String
    )

    data class TestDataB(
        val name: String
    )

    @Test
    fun get() {
        val memListOption = MemListOption(MemoryDataConfig())
        assertEquals(memListOption.get("test.a", TestDataA::class), Optional.empty<TestDataA>())
        val createDataA = memListOption.getOrCreate("test.a", TestDataA::class)
        assertEquals(memListOption.get("test.a", TestDataB::class), Optional.empty<TestDataB>())
        assertTrue(createDataA.available)
    }

    @Test
    fun drop() {
        val memListOption = MemListOption(MemoryDataConfig())
        assertFalse(memListOption.drop("test.a", TestDataA::class))
        val createDataA = memListOption.getOrCreate("test.a", TestDataA::class)
        createDataA.items().get().add(TestDataA("TEST"))
        assertTrue(memListOption.drop("test.a", TestDataA::class))
        assertFalse(memListOption.drop("test.a", TestDataA::class))
    }

    @Test
    fun exists() {
        val memListOption = MemListOption(MemoryDataConfig())
        assertFalse(memListOption.exists("test.a", TestDataA::class))
        memListOption.getOrCreate("test.a", TestDataA::class)
        assertTrue(memListOption.exists("test.a", TestDataA::class))
    }
}
