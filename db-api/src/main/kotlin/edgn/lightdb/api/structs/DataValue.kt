package edgn.lightdb.api.structs

import kotlin.reflect.KClass

/**
 * 实例对象
 */
interface DataValue<T : Any> {

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
     */
    fun clear()
}
