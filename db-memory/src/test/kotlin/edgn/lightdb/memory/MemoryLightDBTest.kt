package edgn.lightdb.memory

import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

internal class MemoryLightDBTest {

    @Test
    fun withList() {
        val memoryLightDB = MemoryLightDB()
        val defaultList = memoryLightDB.withList()
        val namedList = memoryLightDB.withList("namedList")
        assertNotEquals(namedList, defaultList)
    }
}
