package edgn.lightdb.api.tables.list

import edgn.lightdb.api.tables.DataValue
import java.util.Optional

/**
 * List 实例
 * @param V : Any 实例持有的类型
 */
interface LightListValue<V : Any> : DataValue<V> {

    /**
     * 添加数据到末尾
     *
     * @param element T 被添加的数据
     */
    fun add(element: V)

    /**
     * 添加数据到指定位置
     *
     * 此方法会改变实例长度,
     * 注意，如果index 超过最大长度则会抛出异常
     *
     * @param index Long 索引
     * @param element T 数据
     */
    fun add(index: Long, element: V)

    /**
     * 根据索引号移除数据
     *
     * @param index Long 索引
     * @return Optional<T> 被移除的数据，如果不存在则返回 NULL
     */
    fun remove(index: Long): Optional<V>

    /**
     *
     * 更改索引位置的数据
     *
     * 注意！如果索引无数据将修改失败且抛出异常
     *
     * @param index Long 索引
     * @param element T 数据
     * @return V 原来的数据
     */
    fun set(index: Long, element: V): V

    /**
     * 根据索引获取数据
     *
     * @param index Long 索引
     * @return Optional<T> 索引的数据，如果不存在则返回 NULL
     */
    fun get(index: Long): Optional<V>

    /**
     * 根据数据获取索引，不存在则返回 -1
     */
    fun indexOf(element: V): Long

    /**
     * 根据数据获取索引，不存在则返回 -1
     */
    fun lastIndexOf(element: V): Long

    /**
     * 排序List
     */
    fun sortWith(comparator: Comparator<V>)

    /**
     * 数据迭代器
     *
     */
    fun values(): Iterator<V>
}