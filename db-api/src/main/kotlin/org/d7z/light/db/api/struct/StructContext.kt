package org.d7z.light.db.api.struct

/**
 * 通用的数据上下文
 */
interface StructContext {
    /**
     * 根据key销毁实例
     *
     * 如果实例不存在则返回false
     **
     */
    fun drop(key: String): Boolean {
        return setTimeout(key, 0)
    }

    /**
     * 判断此key的实例是否存在
     */
    fun exists(key: String): Boolean

    /**
     * 获取过期时间
     *
     * 如果返回小于0则表示此key不存在或已过期
     *
     * @param key String key 键
     * @return Long 过期的时间 （秒）
     */
    fun getTimeout(key: String): Long

    /**
     *
     * 设置过期的时间 （秒）
     *
     * 设置大于 0 表示此键下的数据将在目标秒后过期，如果
     * 等于 0 则表示立即过期，类似于 `drop()`方法
     * 小于 0 则表示清除过期时间，
     *
     * 如果此键不存在或此实现不支持配置则返回 `false`
     *
     * @param key String key 键
     * @param second Long 过期的时间 （秒）
     * @return Boolean 配置结果
     */
    fun setTimeout(key: String, second: Long): Boolean

    /**
     * 清除过期时间
     */
    fun clearTimeout(key: String) {
        setTimeout(key, -1)
    }
}
