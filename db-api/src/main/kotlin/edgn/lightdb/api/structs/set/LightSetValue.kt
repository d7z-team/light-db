package edgn.lightdb.api.structs.set

import edgn.lightdb.api.structs.DataValue

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
