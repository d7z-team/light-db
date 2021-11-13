package edgn.lightdb.memory

import edgn.lightdb.api.LightDB
import edgn.lightdb.api.tables.list.LightListOption
import edgn.lightdb.api.tables.map.LightMapOption
import edgn.lightdb.api.tables.set.LightSetOption

class MemoryLightDB @JvmOverloads constructor(override val config: MemoryConfig = MemoryConfig()) :
    LightDB<MemoryConfig> {

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
