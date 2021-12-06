package edgn.lightdb.jedis

import edgn.lightdb.api.LightDB
import edgn.lightdb.api.tables.list.LightListOption
import edgn.lightdb.api.tables.map.LightMapOption
import edgn.lightdb.api.tables.set.LightSetOption

class JedisLightDB
@JvmOverloads
constructor(
    override val config: JedisDataConfig = JedisDataConfig()
) : LightDB<JedisDataConfig> {

    override fun withMap(name: String): LightMapOption {
        TODO("Not yet implemented")
    }

    override fun withList(name: String): LightListOption {
        TODO("Not yet implemented")
    }

    override fun withSet(name: String): LightSetOption {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}
