package edgn.lightdb.api

import edgn.lightdb.api.format.IDataDefaultFormat
import edgn.lightdb.api.format.IDataFormat
import kotlin.reflect.KClass

/**
 * lightDB 实例配置
 */
interface ILightDBConfig {
    /**
     * 默认的数据转换工具
     */
    var defaultDataFormat: IDataDefaultFormat

    /**
     * 注册自定义类型转换器
     */
    fun <T : Any> registerDataFormat(clazz: KClass<T>, format: IDataFormat<T>)
}
