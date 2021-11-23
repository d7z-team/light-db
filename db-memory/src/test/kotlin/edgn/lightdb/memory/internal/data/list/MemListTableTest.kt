package edgn.lightdb.memory.internal.data.list

import edgn.lightdb.api.DestroyException
import edgn.lightdb.api.tables.list.LightListTable
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.Optional
import java.util.concurrent.TimeUnit

internal class MemListTableTest {

    data class TestData(
        val data: String
    )

    @Test
    fun testExpire() {
        val table: LightListTable<TestData> = MemListTable("test", TestData::class)
        assertTrue(table.available)
        table.expire(-1, TimeUnit.SECONDS)
        assertFalse(table.available)
        assertThrows<DestroyException> {
            table.clearExpire()
        }
        assertEquals(Optional.empty<LightListTable<TestData>>(), table.items())
    }
}
