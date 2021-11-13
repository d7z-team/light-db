package edgn.lightdb.api

import edgn.lightdb.api.tables.list.LightListOption
import edgn.lightdb.api.tables.map.LightMapOption
import edgn.lightdb.api.tables.set.LightSetOption

/**
 * LightDB 主体
 */
interface LightDB<CFG : LightDBConfig> {
    /**
     * 此 LightDB 配置
     */
    val config: CFG

    /**
     * Map 相关
     */
    fun withMap(name: String = "_default"): LightMapOption

    /**
     * List 相关
     */
    fun withList(name: String = "_default"): LightListOption

    /**
     * Set 相关
     */
    fun withSet(name: String = "_default"): LightSetOption
}
