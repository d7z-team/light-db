package edgn.lightdb.jedis.internal.utils

/**
 * 环境配置加载器
 */
class ConfigLoaderUtils(private val source: Map<String, String>) {
    /**
     * 获取String配置
     */
    fun getString(key: String, defaultValue: String): String {
        return source.getOrDefault(key, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return source.getOrDefault(key, defaultValue.toString()).toIntOrNull() ?: defaultValue
    }
}
