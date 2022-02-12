package org.d7z.light.db.modules.cache.api

import java.util.Optional

interface ICacheContent<K : Any, V : Any> {
    /**
     * 缓存的分组名称
     */
    val group: String

    /**
     * 保存会话缓存
     *
     * 注意，多次调用会覆盖之前的缓存
     *
     * @param key K 入参
     * @param value V 目标数据
     * @return Optional<V> 如果存在历史数据则返回，否则返回`Optional.empty<V>()`
     */
    fun save(key: K, value: V): Optional<V>

    /**
     * 如果现有缓存不存在则保存缓存
     *
     * @param key K 入参
     * @param value V 目标数据
     * @return Optional<V> 如果存在历史数据则返回历史数据，否则返回`Optional.empty<V>()`
     */
    fun saveIfAbsent(key: K, value: V): V

    /**
     * 删除缓存
     * @param key K 入参
     * @return Boolean 如果缓存存在且删除则返回 true 否则 false
     */
    fun remove(key: K): Boolean

    /**
     * 获取缓存
     *
     * @param key K 入参
     * @return Optional<V> 如果缓存存在则返回数据，否则返回`Optional.empty<V>()`
     */
    fun get(key: K): Optional<V>

    /**
     * 根据入参判断缓存是否存在
     */
    fun exists(key: K): Boolean

    /**
     * 清除此分组下的所有缓存
     */
    fun clear()
}
