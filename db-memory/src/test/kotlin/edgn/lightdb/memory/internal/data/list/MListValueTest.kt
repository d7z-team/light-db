package edgn.lightdb.memory.internal.data.list

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class MListValueTest {
    data class TestData(
        val data: String
    )

    @Test
    fun getSize() {
        val data = MListTable("test", TestData::class).items().get()
        assertEquals(data.size, 0)
        data.add(TestData("12"))
        assertEquals(data.size, 1)
    }

    @Test
    fun clear() {
        val data = MListTable("test", TestData::class).items().get()
        data.add(TestData("12"))
        assertEquals(data.size, 1)
        data.clear()
        assertEquals(data.size, 0)
    }

    @Test
    fun remove() {
        val data = MListTable("test", TestData::class).items().get()
        data.add(TestData("11"))
        data.add(TestData("12"))
        data.add(TestData("13"))
        assertEquals(data.size, 3)
        assertEquals(data.remove(2).get().data, "13")
        assertEquals(data.size, 2)
        assertEquals(data.get(1).get().data, "12")
    }

    @Test
    fun set() {
        val data = MListTable("test", TestData::class).items().get()
        data.add(TestData("11"))
        data.add(TestData("12"))
        data.add(TestData("13"))
        assertEquals(data.get(1).get().data, "12")
        data.set(1, TestData("-1"))
        assertEquals(data.get(1).get().data, "-1")
    }

    @Test
    fun indexOf() {
        val data = MListTable("test", TestData::class).items().get()
        val first = TestData("11")
        val second = TestData("12")
        val third = TestData("13")
        data.add(first)
        data.add(second)
        data.add(third)
        assertEquals(data.indexOf(second), 1)
        assertEquals(data.lastIndexOf(second), 1)
    }

    @Test
    fun sortWith() {
        val data = MListTable("test", Int::class).items().get()
        data.add(2)
        data.add(1)
        data.add(3)
        assertEquals(data.values().asSequence().toList(), listOf(2, 1, 3))
        data.sortWith { o1, o2 -> o1 - o2 }
        assertEquals(data.values().asSequence().toList(), listOf(1, 2, 3))
    }
}
