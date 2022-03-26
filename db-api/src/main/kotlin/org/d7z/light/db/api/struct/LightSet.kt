package org.d7z.light.db.api.struct

import kotlin.reflect.KClass

interface LightSet<V : Any> {
    /**
     * 数据类型
     */
    val valueType: KClass<V>

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

    /**
     * 实例数量
     */
    val size: Long
}
