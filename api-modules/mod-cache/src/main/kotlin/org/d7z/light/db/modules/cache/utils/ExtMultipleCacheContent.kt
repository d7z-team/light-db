package org.d7z.light.db.modules.cache.utils

import org.d7z.light.db.modules.cache.api.ICacheContext
import org.d7z.light.db.modules.cache.api.IMultipleCacheContent
import org.d7z.light.db.modules.cache.api.IMultipleKey
import org.d7z.light.db.modules.cache.internal.CacheContext

/**
 * 多入参缓存的扩展方法
 *
 * 使用此扩展包裹对应的业务代码，即可实现自动化缓存
 *
 */
inline fun <reified V : Any>
IMultipleCacheContent<V>.cacheContext(
    key: IMultipleKey,
    noinline execute: () -> V?,
): ICacheContext<IMultipleKey, V> {
    return CacheContext(this, key, execute)
}

/**
 * 多入参缓存的扩展方法
 *
 * 使用此扩展包裹对应的业务代码，即可实现自动化缓存
 *
 */
inline fun <reified V : Any>
IMultipleCacheContent<V>.cacheContext(
    vararg key: Any,
    noinline execute: () -> V?,
): ICacheContext<IMultipleKey, V> {
    return CacheContext(this, IMultipleKey.keysOf(*key), execute)
}

/**
 * 缓存的扩展方法
 *
 * 使用此扩展包裹对应业务代码，即可实现清除缓存
 */
inline fun <reified V : Any, reified R : Any>
IMultipleCacheContent<V>.cacheWriteContext(
    vararg key: Any,
    noinline execute: () -> R,
): R {
    val result = execute()
    this.remove(IMultipleKey.keysOf(*key))
    return result
}
