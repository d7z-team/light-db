package edgn.lightdb.api.tables.map

import edgn.lightdb.api.tables.DataValue
import java.util.Optional

/**
 * Map 实例
 * @param V : Any 包装的数据
 */
interface LightMapValue<V : Any> : DataValue<V> {
    /**
     * 提交/覆盖数据
     *
     * 注意：提交的数据会覆盖旧的数据
     *
     * @param key String 键
     * @param value V 数据
     * @return Optional<V> 如果数据已存在则返回旧的数据，否则返回NULL
     */
    fun put(key: String, value: V): Optional<V>

    /**
     *
     * 提交数据
     *
     * 提交但不覆盖数据
     *
     * @param key String 键
     * @param value V 数据
     * @return Optional<V> 如果数据已存在则返回旧的数据，否则返回NULL
     */
    fun putIfAbsent(key: String, value: V): Optional<V>

    /**
     * 如果映射包含指定的键，则返回true
     */
    fun containsKey(key: String): Boolean

    /**
     * 根据 Key 获取数据
     * @param key String 键
     * @return Optional<V> 返回 key 对应的数据，如果不存在则返回 NULL
     */
    fun get(key: String): Optional<V>

    /**
     * 数据迭代器
     *
     * 注意，如果数据长度发生变化将触发迭代错误
     *
     */
    fun keys(): MutableIterator<String>
}
