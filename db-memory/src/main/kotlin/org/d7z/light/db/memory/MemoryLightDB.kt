package org.d7z.light.db.memory

import org.d7z.light.db.api.LightDB
import org.d7z.light.db.api.struct.ListContext
import org.d7z.light.db.api.struct.MapContext
import org.d7z.light.db.api.struct.SetContext
import org.d7z.light.db.memory.structs.list.MemListContext
import org.d7z.light.db.memory.structs.map.MemMapContext
import org.d7z.light.db.memory.structs.set.MemSetContext
import java.util.Timer
import java.util.TimerTask
import java.util.concurrent.ConcurrentHashMap

/**
 * LightDB 内存后端实现
 *
 * @property refreshTime Long 内部数据刷新时间
 */
class MemoryLightDB @JvmOverloads constructor(
    private val refreshTime: Long = 60,
) : LightDB, TimerTask() {

    private val listNamespace = ConcurrentHashMap<String, MemListContext>()
    private val mapNamespace = ConcurrentHashMap<String, MemMapContext>()
    private val setNamespace = ConcurrentHashMap<String, MemSetContext>()

    override val name = "MemoryLightDB"

    private val timer = Timer()

    init {
        timer.schedule(this, refreshTime * 1000, refreshTime * 1000)
    }

    override fun withList(name: String): ListContext {
        return listNamespace.getOrPut(key = name) {
            MemListContext()
        }
    }

    override fun withMap(name: String): MapContext {
        return mapNamespace.getOrPut(key = name) {
            MemMapContext()
        }
    }

    override fun withSet(name: String): SetContext {
        return setNamespace.getOrPut(key = name) {
            MemSetContext()
        }
    }

    @Synchronized
    override fun close() {
        timer.cancel()
        listNamespace.values.forEach { it.container.clear() }
        mapNamespace.values.forEach { it.container.clear() }
        setNamespace.values.forEach { it.container.clear() }
    }

    override fun run() {
        // 刷新数据缓存
        listNamespace.values.forEach { it.container.refresh() }
        mapNamespace.values.forEach { it.container.refresh() }
        setNamespace.values.forEach { it.container.refresh() }
    }
}
