package edgn.lightdb.memory

import edgn.lightdb.api.LightDB
import edgn.lightdb.api.structs.list.LightListGroup
import edgn.lightdb.api.structs.map.LightMapGroup
import edgn.lightdb.api.structs.set.LightSetGroup
import edgn.lightdb.memory.internal.data.list.MemListGroup
import edgn.lightdb.memory.internal.data.map.MemMapGroup
import edgn.lightdb.memory.internal.data.set.MemSetGroup
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicBoolean

/**
 * LightDB 内存实现,仅作为默认测试使用，切勿用于生产环境
 */
class MemoryLightDB : LightDB {

    private val closed = AtomicBoolean(false)

    private val listNamespace = ConcurrentHashMap<String, MemListGroup>()
    private val mapNamespace = ConcurrentHashMap<String, MemMapGroup>()
    private val setNamespace = ConcurrentHashMap<String, MemSetGroup>()

    override fun withList(name: String): LightListGroup {
        checkClose()
        return listNamespace.getOrPut(key = name) {
            MemListGroup()
        }
    }

    override fun withMap(name: String): LightMapGroup {
        checkClose()
        return mapNamespace.getOrPut(key = name) {
            MemMapGroup()
        }
    }

    override fun withSet(name: String): LightSetGroup {
        checkClose()
        return setNamespace.getOrPut(key = name) {
            MemSetGroup()
        }
    }

    private fun checkClose() {
        if (closed.get()) {
            throw RuntimeException("此实例已被销毁.")
        }
    }

    @Synchronized
    override fun close() {
        closed.set(true)
        TODO("Not yet implemented")
    }
}
