package edgn.lightdb.jedis.options

import edgn.lightdb.jedis.internal.utils.JsonDataCovertUtils

data class JedisLightDBConfig(
    /**
     * 开启对象缓存
     */
    val cache: Boolean = true,
    val dataCovert: DataCovert = JsonDataCovertUtils()

)
