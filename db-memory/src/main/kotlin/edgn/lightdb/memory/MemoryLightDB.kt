package edgn.lightdb.memory

import edgn.lightdb.api.LightDB
import edgn.lightdb.api.tables.list.LightListOption
import edgn.lightdb.api.tables.map.LightMapOption
import edgn.lightdb.api.tables.set.LightSetOption
import edgn.lightdb.memory.internal.data.list.MemListOption
import edgn.lightdb.memory.internal.data.map.MemMapOption
import edgn.lightdb.memory.internal.data.set.MemSetOption
import edgn.lightdb.memory.internal.universal.DataRefresh
import java.util.Timer
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.concurrent.timerTask

/**
 * LightDB 内存实现,仅作为默认测试使用，切勿用于生产环境
 */
class MemoryLightDB @JvmOverloads constructor(
    override val config: MemoryDataConfig = MemoryDataConfig()
) :
    LightDB<MemoryDataConfig>, DataRefresh {
    private val timer = Timer()
    private val closed = AtomicBoolean(false)

    init {
        if (config.autoRefresh) {
            timer.schedule(
                timerTask {
                    refresh()
                },
                10000, 60_000
            )
        }
    }

    private val listNamespace = ConcurrentHashMap<String, MemListOption>()
    private val mapNamespace = ConcurrentHashMap<String, MemMapOption>()
    private val setNamespace = ConcurrentHashMap<String, MemSetOption>()

    override fun withList(name: String): LightListOption {
        checkClose()
        return listNamespace.getOrPut(key = name) {
            MemListOption(config)
        }
    }

    override fun withMap(name: String): LightMapOption {
        checkClose()
        return mapNamespace.getOrPut(key = name) {
            MemMapOption(config)
        }
    }

    override fun withSet(name: String): LightSetOption {
        checkClose()
        return setNamespace.getOrPut(key = name) {
            MemSetOption(config)
        }
    }

    override fun refresh() {
        checkClose()
        listNamespace.forEach { (_, u) -> u.refresh() }
        mapNamespace.forEach { (_, u) -> u.refresh() }
        setNamespace.forEach { (_, u) -> u.refresh() }
    }

    private fun checkClose() {
        if (closed.get()) {
            throw RuntimeException("此实例已被销毁.")
        }
    }

    @Synchronized
    override fun close() {
        if (closed.get()) {
            return
        }
        if (closed.weakCompareAndSetAcquire(false, true).not()) {
            return
        }
        timer.cancel()
        listNamespace.forEach { (_, u) -> u.close() } // TODO: 可能存在线程安全问题
        mapNamespace.forEach { (_, u) -> u.close() }
        setNamespace.forEach { (_, u) -> u.close() }
        listNamespace.clear()
        mapNamespace.clear()
        setNamespace.clear()
    }
}
