package org.d7z.light.db.spring.boot

import org.d7z.light.db.api.LightDB
import org.d7z.light.db.memory.MemoryLightDB
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [Application::class], properties = ["spring.data.light-db.mode=memory"]
)
internal class LightDBMemoryTest {

    @Autowired
    private lateinit var lightDB: LightDB

    @Test
    fun lightMemoryDB() {
        assertEquals(lightDB::class, MemoryLightDB::class)
    }
}
