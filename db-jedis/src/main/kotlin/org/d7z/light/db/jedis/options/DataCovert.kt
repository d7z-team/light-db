package org.d7z.light.db.jedis.options

import org.d7z.light.db.api.LightDBException
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
     * 数据转换异常
     */
    class CovertErrorException(msg: String, e: Exception? = null) : LightDBException(msg, e)
}
