package edgn.lightdb.utils.format

import edgn.lightdb.api.format.IDataFormat
import kotlin.reflect.KClass

class DataDefaultFormat : IDataFormat {
    override fun <T : Any> format(data: T, clazz: KClass<T>): String {
        TODO("Not yet implemented")
    }

    override fun <T : Any> parse(data: String, clazz: KClass<T>): T {
        TODO("Not yet implemented")
    }
}
