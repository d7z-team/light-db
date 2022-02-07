package edgn.lightdb.modules.cache.internal

import edgn.lightdb.modules.cache.LightCache
import edgn.lightdb.modules.cache.api.ILightCache
import edgn.lightdb.modules.cache.utils.cacheContext
import edgn.lightdb.modules.cache.utils.singleCacheGroup
import org.junit.jupiter.api.Test

internal class CacheContextTest {

    @Test
    fun test() {
        val testService = TestService(LightCache())
        println(testService.get("not-found"))
    }

    class TestService(lightCache: ILightCache) {
        private val content = lightCache.singleCacheGroup<String, String>("test")
        val dataSource = HashMap<String, String>() // dataSource

        fun get(name: String): String = content.cacheContext(name) {
            dataSource[name]
        }.default("ERROR").execute()
    }
}
