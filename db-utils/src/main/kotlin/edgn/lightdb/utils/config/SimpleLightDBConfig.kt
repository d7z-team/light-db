package edgn.lightdb.utils.config

import edgn.lightdb.api.LightDBConfig
import edgn.lightdb.api.format.IDataFormat
import edgn.lightdb.api.format.ITypedDataFormat
import edgn.lightdb.utils.format.DataDefaultFormat
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicReference
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

abstract class SimpleLightDBConfig : LightDBConfig {
    private val dataFormatMap: MutableMap<String, ITypedDataFormat<*>> = ConcurrentHashMap()

    private val internalDefaultFormat = AtomicReference<IDataFormat>(DataDefaultFormat())
    override var defaultDataFormat: IDataFormat
        get() = internalDefaultFormat.get()
        set(value) {
            internalDefaultFormat.set(value)
        }

    override fun <T : Any> registerDataFormat(clazz: KClass<T>, format: ITypedDataFormat<T>) {
        dataFormatMap[clazz.jvmName] = format
    }

    /**
     * 获取合适的序列化对象
     */
    fun getFormat(clazz: KClass<*>): IDataFormat {
        return dataFormatMap[clazz.jvmName] ?: defaultDataFormat
    }
}
