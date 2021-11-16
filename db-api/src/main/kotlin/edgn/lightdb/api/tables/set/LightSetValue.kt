package edgn.lightdb.api.tables.set

import edgn.lightdb.api.tables.DataValue

interface LightSetValue<V : Any> : DataValue<V> {
    /**
     * 添加数据
     */
    fun add(data: V): Boolean

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
     *
     */
    fun values(): Iterator<V>
}
