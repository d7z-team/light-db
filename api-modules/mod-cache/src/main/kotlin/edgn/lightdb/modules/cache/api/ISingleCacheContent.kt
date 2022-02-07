package edgn.lightdb.modules.cache.api

/**
 * 单入参缓存抽象
 *
 * @param K 缓存 key
 * @param V 缓存 value
 */
interface ISingleCacheContent<K : Any, V : Any> : ICacheContent<K, V>
