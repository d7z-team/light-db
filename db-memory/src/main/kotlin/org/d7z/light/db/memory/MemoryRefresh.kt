package org.d7z.light.db.memory

/**
 * 数据刷新函数，调用此函数后强制刷新内部数据，销毁过期内存
 */
interface MemoryRefresh {
    /**
     * 刷新
     */
    fun refresh()
}
