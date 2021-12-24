package edgn.lightdb.api.structs.map

import edgn.lightdb.api.structs.DataValue
import java.util.Optional
import kotlin.reflect.KClass

/**
 * Map 实例
 * @param V : Any 包装的数据
 */
interface LightMapValue<K : Any, V : Any> : DataValue<V> {

    /**
     * 键 (key) 的数据类型
     */
    val keyType: KClass<K>

    /**
     * 提交/覆盖数据
     *
     * 注意：提交的数据会覆盖旧的数据
     *
     * @param key K 键
     * @param value V 数据
     * @return Optional<V> 如果数据已存在则返回旧的数据，否则返回NULL
     */
    fun put(key: K, value: V): Optional<V>

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
    fun getAndSet(key: K, oldValue: V, newValue: V): Boolean

    /**
     * 如果指定的键尚未与值相关联，则将其与给定值相关联。 否则，用 remapping 函数的结果替换该值
     *
     */
    fun merge(key: K, value: V, remapping: (oldValue: V, newValue: V) -> V): V

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
     * 数据迭代器
     *
     * 注意，如果数据长度发生变化将触发迭代错误
     *
     */
    fun keys(): Iterator<K>
}
