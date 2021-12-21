package edgn.lightdb.jedis

import edgn.lightdb.api.LightDB
import edgn.lightdb.api.structs.list.LightListGroup
import edgn.lightdb.api.structs.map.LightMapGroup
import edgn.lightdb.api.structs.set.LightSetGroup

class JedisLightDB
@JvmOverloads
constructor(
    override val config: JedisDataConfig = JedisDataConfig()
) : LightDB<JedisDataConfig> {

    override fun withMap(name: String): LightMapGroup {
        TODO("Not yet implemented")
    }

    override fun withList(name: String): LightListGroup {
        TODO("Not yet implemented")
    }

    override fun withSet(name: String): LightSetGroup {
        TODO("Not yet implemented")
    }

    override fun close() {
        TODO("Not yet implemented")
    }
}
