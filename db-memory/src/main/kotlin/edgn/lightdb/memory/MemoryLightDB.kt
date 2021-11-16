package edgn.lightdb.memory

import edgn.lightdb.api.LightDB
import edgn.lightdb.api.tables.list.LightListOption
import edgn.lightdb.api.tables.map.LightMapOption
import edgn.lightdb.api.tables.set.LightSetOption
import edgn.lightdb.memory.internal.impl.list.MListOption
import edgn.lightdb.memory.internal.impl.map.MMapOption
import edgn.lightdb.memory.internal.impl.set.MSetOption
import edgn.lightdb.memory.internal.refresh.DataRefresh
import edgn.lightdb.memory.internal.universal.MemoryDataConfig
import edgn.lightdb.memory.internal.universal.MemoryDataTable
import java.util.concurrent.ConcurrentHashMap

class MemoryLightDB @JvmOverloads constructor(
    override val config: MemoryDataConfig<MemoryDataTable<Any>> = MemoryDataConfig()
) :
    LightDB<MemoryDataConfig<MemoryDataTable<Any>>>, DataRefresh {
    private val listNamespace = ConcurrentHashMap<String, MListOption>()
    private val mapNamespace = ConcurrentHashMap<String, MMapOption>()
    private val setNamespace = ConcurrentHashMap<String, MSetOption>()

    override fun withList(name: String): LightListOption {
        return listNamespace.getOrPut(key = name) {
            MListOption(config)
        }
    }

    override fun withMap(name: String): LightMapOption {
        return mapNamespace.getOrPut(key = name) {
            MMapOption(config)
        }
    }

    override fun withSet(name: String): LightSetOption {
        return setNamespace.getOrPut(key = name) {
            MSetOption(config)
        }
    }

    override fun refresh() {
        listNamespace.forEach { (_, u) -> u.refresh() }
        mapNamespace.forEach { (_, u) -> u.refresh() }
        setNamespace.forEach { (_, u) -> u.refresh() }
    }
}
