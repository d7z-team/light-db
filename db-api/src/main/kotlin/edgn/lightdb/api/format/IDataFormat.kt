package edgn.lightdb.api.format

import kotlin.reflect.KClass

/**
 * 数据序列化工具
 */
interface IDataFormat {
    /**
     * 对数据序列化
     */
    fun <T : Any> format(data: T, clazz: KClass<T>): String

    /**
     * 对数据反序列化
     */
    fun <T : Any> parse(data: String, clazz: KClass<T>): T
}
