package edgn.lightdb.memory

import edgn.lightdb.api.LightDB
import edgn.lightdb.api.tables.list.LightListOption
import edgn.lightdb.api.tables.map.LightMapOption
import edgn.lightdb.api.tables.set.LightSetOption
import edgn.lightdb.utils.config.SimpleLightDBConfig

class MemoryLightDB : LightDB<SimpleLightDBConfig> {

    override val config = MemoryConfig()

    override fun withList(name: String): LightListOption {
        TODO()
    }

    override fun withMap(name: String): LightMapOption {
        TODO("Not yet implemented")
    }

    override fun withSet(name: String): LightSetOption {
        TODO("Not yet implemented")
    }
}
