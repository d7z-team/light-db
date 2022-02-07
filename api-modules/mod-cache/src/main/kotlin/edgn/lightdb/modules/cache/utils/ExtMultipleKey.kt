package edgn.lightdb.modules.cache.utils

import edgn.lightdb.modules.cache.api.IMultipleKey

fun keysOf(vararg key: Any): IMultipleKey {
    return IMultipleKey.keysOf(*key)
}
