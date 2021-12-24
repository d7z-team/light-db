package edgn.lightdb.api.structs

import edgn.lightdb.api.support.config.DataValueOptions
import kotlin.reflect.KClass

/**
 * 实例对象
 */
interface DataValue<T : Any> : DataValueOptions {

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
