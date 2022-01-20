package edgn.lightdb.jedis.options

import edgn.lightdb.api.LightDBException
import kotlin.reflect.KClass

/**
 * 数据转换
 */
interface DataCovert {

    fun <T : Any> format(data: T, clazz: KClass<out T> = data::class): String

    fun <T : Any> reduce(format: String, clazz: KClass<T>): T

    class CovertErrorException(msg: String, e: Exception? = null) : LightDBException(msg, e)
}
