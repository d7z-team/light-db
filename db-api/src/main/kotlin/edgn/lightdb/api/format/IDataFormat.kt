package edgn.lightdb.api.format

import java.lang.ClassCastException
import kotlin.reflect.KClass

/**
 * 数据序列化/反序列化工具
 */
interface IDataFormat<S : Any> : IDataDefaultFormat {
    /**
     * 支持的序列化类型
     */
    val support: KClass<S>

    /**
     * 对数据序列化
     */
    fun format(data: S): String

    /**
     * 对数据反序列化
     */
    fun parse(data: String): S

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> format(data: T, clazz: KClass<T>): String {
        if (clazz == support) {
            return format(data as S)
        } else {
            throw ClassCastException("not support $clazz, support $support only .")
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> parse(data: String, clazz: KClass<T>): T {
        if (clazz == support) {
            return parse(data) as T
        } else {
            throw ClassCastException("not support $clazz, support $support only .")
        }
    }
}
