package org.d7z.light.db.modules.cache.internal

import org.d7z.light.db.modules.cache.api.IMultipleKey

/**
 * 简单的多入参计算
 */
class SimpleMultipleKey(vararg data: Any) : IMultipleKey {
    override val singleKey: String = data.joinToString(separator = "#")
    override fun toString(): String {
        return singleKey
    }
}
