package edgn.lightdb.modules.cache.internal

import edgn.lightdb.modules.cache.LightCache
import edgn.lightdb.modules.cache.api.IMultipleKey.Companion.keysOf
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class MultipleCacheContentTest {

    @Test
    fun save() {
        LightCache().multipleCacheGroup("test", String::class).let {
            it.save(keysOf("12", "23", "32"), "add")
            assertTrue(it.save(keysOf("12", "23", "34"), "add").isEmpty)
        }
    }
}
