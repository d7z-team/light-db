package edgn.lightdb.memory.internal.utils

interface IDataModules {
    /**
     *  此 module 是否可用 （如不可用将被销毁）
     */
    val available: Boolean

    /**
     * 销毁
     */
    fun clear()
}
