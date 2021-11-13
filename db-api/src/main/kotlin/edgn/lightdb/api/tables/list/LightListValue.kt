package edgn.lightdb.api.tables.list

import edgn.lightdb.api.tables.DataValue
import java.util.Optional

/**
 * List 实例
 * @param T : Any 实例持有的类型
 */
interface LightListValue<T : Any> : DataValue<T> {

    /**
     * 添加数据到末尾
     *
     * @param element T 被添加的数据
     */
    fun add(element: T)

    /**
     * 添加数据到指定位置
     *
     * 此方法会改变实例长度
     *
     * @param index Long 索引
     * @param element T 数据
     */
    fun add(index: Long, element: T)

    /**
     * 根据索引号移除数据
     *
     * @param index Long 索引
     * @return Optional<T> 被移除的数据，如果不存在则返回 NULL
     */
    fun remove(index: Long): Optional<T>

    /**
     *
     * 更改索引位置的数据
     *
     * 注意！如果索引无数据将修改失败且抛出异常
     *
     * @param index Long 索引
     * @param element T 数据
     * @return Optional<T> 原来的数据，如果不存在则返回 NULL
     */
    fun set(index: Long, element: T): Optional<T>

    /**
     * 根据索引获取数据
     *
     * @param index Long 索引
     * @return Optional<T> 索引的数据，如果不存在则返回 NULL
     */
    fun get(index: Long): Optional<T>
}
