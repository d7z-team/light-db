package edgn.lightdb.jedis.options

import edgn.lightdb.api.LightDBException
import kotlin.reflect.KClass

/**
 * 数据序列化工厂
 */
interface DataCovert {

    /**
     *
     * 将目标对象序列化成字符串
     */
    fun <T : Any> format(data: T, clazz: KClass<out T> = data::class): String

    /**
     * 将字符串反序列化为对象
     */
    fun <T : Any> reduce(format: String, clazz: KClass<T>): T

    /**
     *  字符串完整性校验
     *
     *  如果字符串无法转换成对应实体则返回 false
     */
    fun checkFormat(format: String, clazz: KClass<out Any>): Boolean

    /**
     * 数据转换异常
     */
    class CovertErrorException(msg: String, e: Exception? = null) : LightDBException(msg, e)
}
