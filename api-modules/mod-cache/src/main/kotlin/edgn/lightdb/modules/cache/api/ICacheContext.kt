package edgn.lightdb.modules.cache.api

/**
 * 缓存上下文
 * @param V 缓存的 value
 */
interface ICacheContext<K, V> {
    /**
     * 缓存拦截器
     *
     * @param filter Function1<V?, Boolean> 拦截器函数，如果返回 false 则表示不缓存
     * @return ICacheContext<K, V> 当前上下文
     */
    fun filter(filter: (V) -> Boolean): ICacheContext<K, V>

    /**
     * 缓存被拦截或返回 null 时返回的默认数据
     *
     * 注意：此数据会被缓存
     *
     * @param def V 默认数据
     * @return ICacheContext<K, V> 当前上下文
     */
    fun default(def: V): ICacheContext<K, V>

    /**
     * 应用当前逻辑并返回对应结果
     *
     * 注意：如果缓存被拦截且无默认数据则抛出 `NoSuchElementException` 异常
     *
     * @return V 对应结果
     */
    @Throws(NoSuchElementException::class)
    fun execute(defaultFun: () -> V = { throw java.util.NoSuchElementException("No value present") }): V

    /**
     *
     * 应用当前逻辑并返回对应结果
     *
     * 注意：如果缓存被拦截且无默认数据则返回 `defaultReturn` ，返回的 defaultReturn 不会被缓存，如果未被拦截则缓存数据
     *
     * @param defaultReturn V 默认返回结果
     * @return V 对应结果
     */
    fun execute(defaultReturn: V): V
}
