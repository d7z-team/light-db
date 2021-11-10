package edgn.lightdb.utils.config

import edgn.lightdb.api.ILightDBConfig
import edgn.lightdb.api.format.IDataDefaultFormat
import edgn.lightdb.api.format.IDataFormat
import edgn.lightdb.utils.format.DataDefaultFormat
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicReference
import kotlin.reflect.KClass
import kotlin.reflect.jvm.jvmName

abstract class LightDBConfig : ILightDBConfig {
    private val dataFormatMap: MutableMap<String, IDataFormat<*>> = ConcurrentHashMap()

    private val internalDefaultFormat = AtomicReference<IDataDefaultFormat>(DataDefaultFormat())
    override var defaultDataFormat: IDataDefaultFormat
        get() = internalDefaultFormat.get()
        set(value) {
            internalDefaultFormat.set(value)
        }

    override fun <T : Any> registerDataFormat(clazz: KClass<T>, format: IDataFormat<T>) {
        dataFormatMap[clazz.jvmName] = format
    }
}
