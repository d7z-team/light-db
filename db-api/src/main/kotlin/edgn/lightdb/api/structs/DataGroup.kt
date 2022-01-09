package edgn.lightdb.api.structs

/**
 * 一组数据集合
 */
interface DataGroup {
    /**
     * 根据key销毁实例
     *
     * 如果实例不存在则返回false
     * 注意，销毁后所有与此 key 相关的实例均会失效且抛出异常,
     * 在执行销毁时请注意引用
     *
     * 你可以使用实例的 `clear()` 方法清空
     *
     */
    fun drop(key: String): Boolean

    /**
     * 判断此key的实例是否存在
     */
    fun exists(key: String): Boolean
}
