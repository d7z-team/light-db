package org.d7z.light.db.modules.cache.utils

import org.d7z.light.db.modules.cache.api.ILightCache
import org.d7z.light.db.modules.cache.api.IMultipleCacheContent
import org.d7z.light.db.modules.cache.api.ISingleCacheContent

inline fun <reified K : Any, reified V : Any> ILightCache.singleCacheGroup(name: String): ISingleCacheContent<K, V> {
    return this.singleCacheGroup(name, K::class, V::class)
}

inline fun <reified V : Any> ILightCache.multipleCacheGroup(name: String): IMultipleCacheContent<V> {
    return this.multipleCacheGroup(name, V::class)
}
