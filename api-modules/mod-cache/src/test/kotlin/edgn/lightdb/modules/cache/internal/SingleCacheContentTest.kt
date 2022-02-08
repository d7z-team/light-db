package edgn.lightdb.modules.cache.internal

import edgn.lightdb.modules.cache.LightCache
import edgn.lightdb.modules.cache.api.ILightCache
import edgn.lightdb.modules.cache.utils.cacheContext
import edgn.lightdb.modules.cache.utils.cacheWriteContext
import edgn.lightdb.modules.cache.utils.singleCacheGroup
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
        private val content = lightCache.singleCacheGroup<String, String>("test")
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
