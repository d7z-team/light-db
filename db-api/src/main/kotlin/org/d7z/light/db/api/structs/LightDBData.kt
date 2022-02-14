package org.d7z.light.db.api.structs

import kotlin.reflect.KClass

/**
 * 实例对象
 */
interface LightDBData<T : Any> {

    /**
     * 管理实例对象元数据
     *
     * 通过改变元数据来定义底层实现的工作行为
     */
    val meta: MetaData

    /**
     * 数据类型
     */
    val valueType: KClass<T>

    /**
     * 实例数量
     */
    val size: Long

    /**
     * 清空实例下所有数据
     *
     * 注意，清空内容可能会被标记
     */
    fun clear()
}
