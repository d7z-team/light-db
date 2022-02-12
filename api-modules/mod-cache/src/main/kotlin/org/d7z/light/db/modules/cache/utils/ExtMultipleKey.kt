package org.d7z.light.db.modules.cache.utils

import org.d7z.light.db.modules.cache.api.IMultipleKey

fun keysOf(vararg key: Any): IMultipleKey {
    return IMultipleKey.keysOf(*key)
}
