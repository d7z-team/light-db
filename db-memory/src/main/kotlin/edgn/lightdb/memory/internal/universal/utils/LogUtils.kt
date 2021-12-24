package edgn.lightdb.memory.internal.universal.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.Marker
import org.slf4j.MarkerFactory
import kotlin.reflect.KClass

object LogUtils {
    fun getLogger(clazz: KClass<*>): Logger {
        return LoggerFactory.getLogger(clazz.java)
    }

    val marker: Marker = MarkerFactory.getMarker("db-memory")
}

inline fun <reified T : Any> T.getLogger(): Logger {
    return when (this) {
        is KClass<*> -> {
            LogUtils.getLogger(this)
        }
        is Class<*> -> {
            LogUtils.getLogger(this.kotlin)
        }
        else -> {
            LogUtils.getLogger(T::class)
        }
    }
}
