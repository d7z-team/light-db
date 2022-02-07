package edgn.lightdb.modules.cache.utils

import edgn.lightdb.modules.cache.api.ILightCache
import edgn.lightdb.modules.cache.api.IMultipleCacheContent
import edgn.lightdb.modules.cache.api.ISingleCacheContent

inline fun <reified K : Any, reified V : Any> ILightCache.singleCacheGroup(name: String): ISingleCacheContent<K, V> {
    return this.singleCacheGroup(name, K::class, V::class)
}

inline fun <reified V : Any> ILightCache.multipleCacheGroup(name: String): IMultipleCacheContent<V> {
    return this.multipleCacheGroup(name, V::class)
}
