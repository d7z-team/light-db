package edgn.lightdb.modules.cache.api

/**
 * 多入参缓存抽象
 *
 * @param V 缓存 value
 */
interface IMultipleCacheContent<V : Any> : ICacheContent<IMultipleKey, V>
