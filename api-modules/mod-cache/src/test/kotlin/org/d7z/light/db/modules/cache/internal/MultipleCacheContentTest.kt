package org.d7z.light.db.modules.cache.internal

import org.d7z.light.db.modules.cache.LightCache
import org.d7z.light.db.modules.cache.api.ILightCache
import org.d7z.light.db.modules.cache.api.IMultipleKey.Companion.keysOf
import org.d7z.light.db.modules.cache.utils.cacheContext
import org.d7z.light.db.modules.cache.utils.cacheWriteContext
import org.d7z.light.db.modules.cache.utils.multipleCacheGroup
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class MultipleCacheContentTest {

    @Test
    fun save() {
        LightCache().multipleCacheGroup("testM", String::class).let {
            it.save(keysOf("12", "23", "32"), "add")
            assertTrue(it.save(keysOf("12", "23", "34"), "add").isEmpty)
        }
    }

    @Test
    fun test() {
        val testService = MultipleService(LightCache())
        val cache = testService.get("not-key-1", "not-key-2")
        assertEquals("NONE", cache)
        testService.set("key-1", "key-2", "data")
        val cache1 = testService.get("key-1", "key-2")
        assertEquals(cache1, "data")
        testService.remove("key-1", "key-2")
    }

    class MultipleService(lightCache: ILightCache) {
        private val map1 = HashMap<String, String>()
        private val map2 = HashMap<String, String>()
        private val content = lightCache.multipleCacheGroup<String>("multipleCacheGroup")
        fun get(key1: String, key2: String): String = content.cacheContext(key1, key2) {
            if (map1[key1] != map2[key2] || map1[key1] == null || map2[key2] == null) {
                "NONE"
            } else {
                map1[key1]
            }
        }.execute("ERROR")

        fun set(key1: String, key2: String, value: String) = content.cacheWriteContext(key1, key2) {
            map1[key1] = value
            map2[key2] = value
        }

        fun remove(key1: String, key2: String) = content.cacheWriteContext(key1, key2) {
            if (map1[key1] == map2[key2] || map1[key1] != null) {
                map1.remove(key1)
                map2.remove(key2)
            }
        }
    }
}
