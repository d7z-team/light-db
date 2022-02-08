package edgn.lightdb.modules.cache.utils

import edgn.lightdb.modules.cache.api.ICacheContext
import edgn.lightdb.modules.cache.api.ISingleCacheContent
import edgn.lightdb.modules.cache.internal.CacheContext

/**
 * 缓存的扩展方法
 *
 * 使用此扩展包裹对应的业务代码，即可实现自动化缓存
 *
 */
inline fun <reified K : Any, reified V : Any>
ISingleCacheContent<K, V>.cacheContext(key: K, noinline execute: () -> V?): ICacheContext<K, V> {
    return CacheContext(this, key, execute)
}

/**
 * 缓存的扩展方法
 *
 * 使用此扩展包裹对应业务代码，即可实现清除缓存
 *
 * 此扩展函数用于数据的修改操作
 */
inline fun <reified K : Any, reified V : Any, reified R : Any>
ISingleCacheContent<K, V>.cacheWriteContext(key: K, noinline execute: () -> R): R {
    val result = execute()
    this.remove(key)
    return result
}
