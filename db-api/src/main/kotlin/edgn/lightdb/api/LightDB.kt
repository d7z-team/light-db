package edgn.lightdb.api

import edgn.lightdb.api.trees.list.LightListDB
import edgn.lightdb.api.trees.map.LightMapDB
import edgn.lightdb.api.trees.set.LightSetDB

/**
 * LightDB 管理
 */
interface LightDB <CFG : ILightDBConfig> {
    /**
     * 此 LightDB 配置
     */
    fun config(): ILightDBConfig

    /**
     * Map 相关
     */
    fun withMap(): LightMapDB

    /**
     * List 相关
     */
    fun withList(): LightListDB

    /**
     * Set 相关
     */
    fun withSet(): LightSetDB
}
