package edgn.lightdb.jedis

import edgn.lightdb.api.LightDB
import edgn.lightdb.api.tables.list.LightListNamespace
import edgn.lightdb.api.tables.map.LightMapNamespace
import edgn.lightdb.api.tables.set.LightSetNamespace

class JedisLightDB
@JvmOverloads
constructor(
    override val config: JedisDataConfig = JedisDataConfig()
) : LightDB<JedisDataConfig> {

    override fun withMap(name: String): LightMapNamespace {
        TODO("Not yet implemented")
    }

    override fun withList(name: String): LightListNamespace {
        TODO("Not yet implemented")
    }

    override fun withSet(name: String): LightSetNamespace {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}
