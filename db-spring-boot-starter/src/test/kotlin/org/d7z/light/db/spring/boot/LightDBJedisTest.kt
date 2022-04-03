package org.d7z.light.db.spring.boot

import org.d7z.light.db.api.LightDB
import org.d7z.light.db.jedis.JedisLightDB
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(
    classes = [Application::class], properties = ["spring.data.light-db.mode=jedis"]
)
internal class LightDBJedisTest {

    @Autowired
    private lateinit var lightDB: LightDB

    @Test
    fun lightMemoryDB() {
        Assertions.assertEquals(lightDB::class, JedisLightDB::class)
    }
}
