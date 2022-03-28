package org.d7z.light.db.test

import org.d7z.light.db.api.LightDB
import org.d7z.logger4k.core.utils.getLogger
import java.util.ServiceLoader

class Application

private val logger by lazy {
    Application::class.getLogger()
}

fun main(args: Array<String>) {
    System.setProperty("logger.level", "INFO")
    logger.info("装入配置：{}", args.joinToString(",", "[", "]"))
    val spi = ServiceLoader.load(LightDB::class.java)
    val impl = spi.toList()
    impl.forEach {
        Test(it).start()
    }
    impl.forEach { it.close() }
}
