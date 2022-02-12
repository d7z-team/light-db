package org.d7z.light.db.jedis.options

import org.d7z.objects.format.GlobalObjectFormat
import org.d7z.objects.format.api.IDataCovert

/**
 * LightDB => Jedis 配置
 */
data class JedisLightDBConfig(
    /**
     * 开启对象缓存
     */
    val cache: Boolean = true,
    /**
     * 对象 <==> 字符串 转换器
     */
    val dataCovert: IDataCovert = GlobalObjectFormat,

    /**
     * 配置全局命名空间
     */
    val namespace: String = "",
) {
    val redisHeader by lazy {
        if (namespace.isEmpty()) {
            ""
        } else {
            "$namespace:"
        }
    }
}
