package edgn.lightdb.memory.internal.utils

import edgn.lightdb.api.DestroyException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.concurrent.TimeUnit

internal class MemoryMetaTest {

    @Test
    fun getAvailable() {
        val memoryMeta = MemoryMeta("")
        assertTrue(memoryMeta.available)
        memoryMeta.expired(1, TimeUnit.MILLISECONDS)
        Thread.sleep(1000) // 最小单位为 1 S
        assertFalse(memoryMeta.available)
    }

    @Test
    fun getTtl() {
        val memoryMeta = MemoryMeta("")
        memoryMeta.expired(1, TimeUnit.MINUTES)
        assertTrue(memoryMeta.ttl in 59..60)
        Thread.sleep(1000)
        assertTrue(memoryMeta.ttl in 58..59)
    }

    @Test
    fun clearExpire() {
        MemoryMeta("").apply {
            expired(1, TimeUnit.MINUTES)
            assertTrue(ttl in 59..60)
            clearExpire()
            assertEquals(ttl, -1)
        }
    }

    @Test
    fun checkAvailable() {
        MemoryMeta("test").apply {
            expired(-1, TimeUnit.MINUTES)
            assertFalse(available)
            assertThrows(DestroyException::class.java) {
                checkAvailable {
                    available
                }
            }.message!!.contains("test").let { assertTrue(it) }
        }
    }

    @Test
    fun get() {
        MemoryMeta("test").apply {
            expired(1, TimeUnit.MINUTES)
            assertTrue(get("db.ttl").get().toLong() in 59..60)
            Thread.sleep(1000)
            assertTrue(get("db.ttl").get().toLong() in 58..59)
        }
    }

    @Test
    fun set() {
        MemoryMeta("test").apply {
            set("db.ttl", "60")
            assertTrue(ttl in 59..60)
            Thread.sleep(1000)
            assertTrue(ttl in 58..59)
        }
    }
}
