package org.d7z.light.db.modules.cache.internal

import org.d7z.light.db.modules.cache.LightCache
import org.d7z.light.db.modules.cache.api.ILightCache
import org.d7z.light.db.modules.cache.utils.cacheContext
import org.d7z.light.db.modules.cache.utils.cacheWriteContext
import org.d7z.light.db.modules.cache.utils.singleCacheGroup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SingleCacheContentTest {

    @Test
    fun test() {
        val testService = TestService(LightCache())
        val cache = testService.get("not-found")
        testService.dataSource["not-found"] = "found"
        assertEquals(testService.get("not-found"), cache)
        testService.dataSource.remove("not-found")
        testService.set("found", "data2")
        assertEquals(testService.get("found"), "data2")
        testService.dataSource.remove("found")
        assertEquals(testService.get("found"), "data2")
        testService.remove("found")
        assertEquals(testService.get("found"), "ERROR")
    }

    class TestService(lightCache: ILightCache) {
        private val content = lightCache.singleCacheGroup<String, String>("singleCacheGroup")
        val dataSource = HashMap<String, String>() // dataSource

        fun get(name: String): String = content.cacheContext(name) {
            dataSource[name]
        }.filter { it.startsWith("d") }
            .default("ERROR").execute()

        fun set(name: String, value: String) = content.cacheWriteContext(name) {
            dataSource[name] = value
        }

        fun remove(name: String): Unit = content.cacheWriteContext(name) {
            dataSource.remove(name)
        }
    }
}
