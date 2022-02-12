package org.d7z.light.db.memory.internal.utils

interface IDataModules {
    /**
     * 此module 不为空
     */
    val isNotEmpty: Boolean

    /**
     *  此 module 是否可用 （如不可用将被销毁）
     */
    val available: Boolean

    /**
     * 销毁
     */
    fun clear()
}
