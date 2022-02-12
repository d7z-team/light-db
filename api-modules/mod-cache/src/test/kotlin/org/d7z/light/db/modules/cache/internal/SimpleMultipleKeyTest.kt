package org.d7z.light.db.modules.cache.internal

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class SimpleMultipleKeyTest {

    @Test
    fun getSingleKey() {
        assertEquals(SimpleMultipleKey(1, 2, 3, "121").singleKey, "1#2#3#121")
    }
}
