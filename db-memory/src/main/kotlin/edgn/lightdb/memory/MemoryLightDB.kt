package edgn.lightdb.memory

import edgn.lightdb.api.LightDB
import edgn.lightdb.api.trees.list.LightListDB
import edgn.lightdb.api.trees.map.LightMapDB
import edgn.lightdb.api.trees.set.LightSetDB

class MemoryLightDB : LightDB<MemoryConfig> {
    private val cfg = MemoryConfig()
    override fun config(): MemoryConfig {
        return cfg
    }

    override fun withMap(): LightMapDB {
        TODO("Not yet implemented")
    }

    override fun withList(): LightListDB {
        TODO("Not yet implemented")
    }

    override fun withSet(): LightSetDB {
        TODO("Not yet implemented")
    }
}
