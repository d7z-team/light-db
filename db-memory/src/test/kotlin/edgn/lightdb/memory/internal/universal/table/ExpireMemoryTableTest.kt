package edgn.lightdb.memory.internal.universal.table

import edgn.lightdb.api.DestroyException
import edgn.lightdb.api.structs.DataValue
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.util.Optional
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

internal class ExpireMemoryTableTest {

    class InnerExpireMemoryTableTest : ExpireMemoryTable<Any>() {
        override val key: String
            get() = "key .. "
        override val available: Boolean
            get() = expired.not()
        override val clazz: KClass<Any>
            get() = throw RuntimeException("Stub!")

        override fun items(): Optional<out DataValue<Any>> {
            throw RuntimeException("Stub!")
        }

        override fun delete() {
        }

        var testDate = 0L

        override val utcDate: Long
            get() = super.utcDate + testDate
    }

    @Test
    fun getExpired() {
        val data = InnerExpireMemoryTableTest()
        assertFalse(data.expired)
        assertEquals(data.expire(TimeUnit.SECONDS), -1)
        data.testDate = -TimeUnit.MINUTES.toSeconds(101)
        data.expire(100, TimeUnit.SECONDS)
        data.testDate = 0
        assertTrue(data.expired)
        val assertThrows = assertThrows<DestroyException> {
            data.expire(100, TimeUnit.SECONDS)
        }
        assertTrue(assertThrows.message!!.contains("已被销毁"))
    }
}
