package edgn.lightdb.api.tables.set

import edgn.lightdb.api.tables.DataValue

interface LightSetValue<V : Any> : DataValue<V> {
    /**
     * 添加数据
     */
    fun add(data: V)

    /**
     * 移除数据
     */
    fun remove(data: V): Boolean

    /**
     * 判断数据是否存在
     */
    fun contains(data: V): Boolean

    /**
     * 数据迭代器
     *
     * 注意，如果数据长度发生变化将触发迭代错误
     *
     */
    fun values(): MutableIterator<V>
}
