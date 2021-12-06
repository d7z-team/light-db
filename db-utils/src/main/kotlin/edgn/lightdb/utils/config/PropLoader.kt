package edgn.lightdb.utils.config

/**
 * 配置加载器
 */
class PropLoader(private val source: Map<String, String>) {
    fun getString(key: String, defaultValue: String): String {
        return source.getOrDefault(key, defaultValue)
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return source.getOrDefault(key, defaultValue.toString()).toIntOrNull() ?: defaultValue
    }
}
