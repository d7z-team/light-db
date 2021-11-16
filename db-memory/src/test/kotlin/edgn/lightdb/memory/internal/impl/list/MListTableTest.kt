package edgn.lightdb.memory.internal.impl.list

import edgn.lightdb.api.tables.list.LightListTable
import org.junit.jupiter.api.Test

internal class MListTableTest {

    data class TestData(
        val data: String
    )

    @Test
    fun getExpired() {
        val table: LightListTable<TestData> = MListTable("test", TestData::class)
        table.expire()
    }

    @Test
    fun checkDestroy() {
    }

    @Test
    fun expire() {
    }

    @Test
    fun clearExpire() {
    }

    @Test
    fun testExpire() {
    }

    @Test
    fun getData() {
    }

    @Test
    fun getAvailable() {
    }

    @Test
    fun get() {
    }

    @Test
    fun delete() {
    }
}
