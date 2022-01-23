package edgn.lightdb.jedis.options

import edgn.lightdb.jedis.internal.utils.JsonDataCovertUtils

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
    val dataCovert: DataCovert = JsonDataCovertUtils()

)
