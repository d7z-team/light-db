package org.d7z.light.db.api.structs.map

import org.d7z.light.db.api.structs.LightDBData
import java.util.Optional
import kotlin.reflect.KClass

/**
 * Map 实例
 * @param V : Any 包装的数据
 */
interface LightMap<K : Any, V : Any> : LightDBData<V> {

    /**
     * 键 (key) 的数据类型
     */
    val keyType: KClass<K>

    /**
     * 提交/覆盖数据
     *
     * 注意：提交的数据会覆盖旧的数据,此函数对应实现可能非原子操作
     *
     * @param key K 键
     * @param value V 数据
     * @return Optional<V> 如果数据已存在则返回旧的数据，否则返回NULL
     */
    fun put(key: K, value: V): Optional<V>

    /**
     * 提交/覆盖数据
     */
    fun put(pair: Pair<K, V>): Optional<V> {
        return this.put(pair.first, pair.second)
    }

    /**
     *
     * 提交数据
     *
     * 提交但不覆盖数据
     *
     * @param key K 键
     * @param value V 数据
     * @return Optional<V> 如果数据已存在则返回旧的数据，否则返回当前数据
     */
    fun putIfAbsent(key: K, value: V): V

    /**
     *  比较并替换
     *
     *  如果当前 key的数据为 oldValue ，则替换成 newValue并返回 true
     *  如果当前 key的数据不为 oldValue 则返回 false
     *  如果 key不存在则直接添加且返回 true
     */
    fun compareAndSwap(key: K, oldValue: V, newValue: V): Boolean

    /**
     * 如果映射包含指定的键，则返回true
     */
    fun containsKey(key: K): Boolean

    /**
     * 根据 Key 获取数据
     * @param key K 键
     * @return Optional<V> 返回 key 对应的数据，如果不存在则返回 NULL
     */
    operator fun get(key: K): Optional<V>

    /**
     * 根据 key 删除数据
     * @param key K
     * @return Optional<V> 如果不存在则返回  null
     */
    fun removeKey(key: K): Optional<V>

    /**
     * 数据迭代器
     *
     * 注意，如果数据长度发生变化将触发迭代错误
     *
     */
    fun keys(): Iterator<K>
}
