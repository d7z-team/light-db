package edgn.lightdb.memory

import edgn.lightdb.api.LightDB
import edgn.lightdb.api.tables.list.LightListOption
import edgn.lightdb.api.tables.map.LightMapOption
import edgn.lightdb.api.tables.set.LightSetOption
import edgn.lightdb.memory.internal.impl.list.MListOption
import edgn.lightdb.memory.internal.refresh.DataRefresh
import edgn.lightdb.memory.internal.universal.MemoryDataConfig
import edgn.lightdb.memory.internal.universal.MemoryDataTable
import java.util.concurrent.ConcurrentHashMap

class MemoryLightDB @JvmOverloads constructor(
    override val config: MemoryDataConfig<MemoryDataTable<Any>> = MemoryDataConfig()
) :
    LightDB<MemoryDataConfig<MemoryDataTable<Any>>>, DataRefresh {
    private val listNamespace = ConcurrentHashMap<String, MListOption>()

    override fun withList(name: String): LightListOption {
        return listNamespace.getOrPut(key = name) {
            MListOption(config)
        }
    }

    override fun withMap(name: String): LightMapOption {
        TODO("Not yet implemented")
    }

    override fun withSet(name: String): LightSetOption {
        TODO("Not yet implemented")
    }

    override fun refresh() {
        listNamespace.forEach { (_, u) -> u.refresh() }
    }
}
