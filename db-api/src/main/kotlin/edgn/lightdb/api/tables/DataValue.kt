package edgn.lightdb.api.tables

/**
 * 实例对象
 */
interface DataValue<T : Any> {
    /**
     * 实例数量
     */
    val size: Long

    /**
     * 清空实例下所有数据
     */
    fun clear()
}
