package edgn.lightdb.memory.internal.universal

interface MemoryRefresh {
    /**
     * 触发刷新
     */
    fun gc()
}
