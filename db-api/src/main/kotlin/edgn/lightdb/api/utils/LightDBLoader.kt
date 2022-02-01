package edgn.lightdb.api.utils

import edgn.lightdb.api.LightDB
import java.util.ServiceLoader
import kotlin.reflect.full.primaryConstructor

/**
 * 提供一个简单的 LightDB 类加载器
 */
object LightDBLoader {
    private val lazyLightDB: LightDB by lazy {
        val props = listOf("lightDB.loader", "lightdb.loader", "lightDb.loader", "light-db.loader")
        for (prop in props) {
            try {
                val property = System.getProperty(prop)
                if (property != null) {
                    val anyClass = Thread.currentThread().contextClassLoader.loadClass(property).kotlin
                    return@lazy anyClass.primaryConstructor!!.call() as LightDB
                }
            } catch (_: Exception) {
            }
        }
        val spi = ServiceLoader.load(LightDB::class.java)
        spi.findFirst().orElseThrow {
            RuntimeException("未在类路径下找到可用的LightDB实现.")
        }
    }

    /**
     * 加载可用的 LightDB 对象
     */
    fun load(): LightDB = lazyLightDB
}
