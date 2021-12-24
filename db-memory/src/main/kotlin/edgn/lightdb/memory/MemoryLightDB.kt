package edgn.lightdb.memory

import edgn.lightdb.api.LightDB
import edgn.lightdb.api.structs.list.LightListGroup
import edgn.lightdb.api.structs.map.LightMapGroup
import edgn.lightdb.api.structs.set.LightSetGroup
import edgn.lightdb.memory.internal.data.list.MemListGroup
import edgn.lightdb.memory.internal.data.map.MemMapGroup
import edgn.lightdb.memory.internal.data.set.MemSetGroup
import edgn.lightdb.memory.internal.universal.MemoryRefresh
import java.util.concurrent.ConcurrentHashMap

/**
 * LightDB 内存后端实现
 */
class MemoryLightDB : LightDB, MemoryRefresh {

    private val listNamespace = ConcurrentHashMap<String, MemListGroup>()
    private val mapNamespace = ConcurrentHashMap<String, MemMapGroup>()
    private val setNamespace = ConcurrentHashMap<String, MemSetGroup>()

    override fun withList(name: String): LightListGroup {
        return listNamespace.getOrPut(key = name) {
            MemListGroup()
        }
    }

    override fun withMap(name: String): LightMapGroup {
        return mapNamespace.getOrPut(key = name) {
            MemMapGroup()
        }
    }

    override fun withSet(name: String): LightSetGroup {
        return setNamespace.getOrPut(key = name) {
            MemSetGroup()
        }
    }

    override fun gc() {
        listNamespace.values.forEach { it.gc() }
        mapNamespace.values.forEach { it.gc() }
        setNamespace.values.forEach { it.gc() }
    }

    @Synchronized
    override fun close() {
        listNamespace.values.forEach { it.close() }
        mapNamespace.values.forEach { it.close() }
        setNamespace.values.forEach { it.close() }
    }
}
