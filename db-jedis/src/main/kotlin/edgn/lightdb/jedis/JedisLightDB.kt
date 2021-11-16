package edgn.lightdb.jedis

import edgn.lightdb.api.LightDB
import edgn.lightdb.api.tables.list.LightListOption
import edgn.lightdb.api.tables.map.LightMapOption
import edgn.lightdb.api.tables.set.LightSetOption

class JedisLightDB : LightDB<JedisLightDBConfig> {
    override val config: JedisLightDBConfig
        get() = TODO("Not yet implemented")

    override fun withMap(name: String): LightMapOption {
        TODO("Not yet implemented")
    }

    override fun withList(name: String): LightListOption {
        TODO("Not yet implemented")
    }

    override fun withSet(name: String): LightSetOption {
        TODO("Not yet implemented")
    }
}
