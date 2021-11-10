package edgn.lightdb.utils.format

import edgn.lightdb.api.format.IDataDefaultFormat
import kotlin.reflect.KClass

class DataDefaultFormat : IDataDefaultFormat {
    override fun <T : Any> format(data: T, clazz: KClass<T>): String {
        TODO("Not yet implemented")
    }

    override fun <T : Any> parse(data: String, clazz: KClass<T>): T {
        TODO("Not yet implemented")
    }
}
