package edgn.lightdb.api.utils

import edgn.lightdb.api.LightDB
import java.util.ServiceLoader

/**
 * 提供一个简单的 LightDB 类加载器
 */
object LightDBLoader {
    /**
     * 加载可用的 LightDB 对象
     */
    fun load(): LightDB {
        val spi = ServiceLoader.load(LightDB::class.java)
        return spi.findFirst().orElseThrow {
            RuntimeException("未在类路径下找到可用的LightDB实现.")
        }
    }
}
