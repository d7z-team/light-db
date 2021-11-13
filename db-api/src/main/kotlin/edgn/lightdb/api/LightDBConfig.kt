package edgn.lightdb.api

import edgn.lightdb.api.format.IDataFormat
import edgn.lightdb.api.format.ITypedDataFormat
import kotlin.reflect.KClass

/**
 * lightDB 实例配置
 */
interface LightDBConfig {
    /**
     * 默认的数据转换工具
     */
    var defaultDataFormat: IDataFormat

    /**
     * 注册自定义类型转换器
     */
    fun <T : Any> registerDataFormat(clazz: KClass<T>, format: ITypedDataFormat<T>)
}
