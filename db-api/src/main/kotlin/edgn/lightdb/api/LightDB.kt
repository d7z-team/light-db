package edgn.lightdb.api

import edgn.lightdb.api.tables.list.LightListOption
import edgn.lightdb.api.tables.map.LightMapOption
import edgn.lightdb.api.tables.set.LightSetOption
import java.io.Closeable

/**
 * LightDB 主体
 */
interface LightDB<CFG : LightDBConfig> : Closeable {
    /**
     * 此 LightDB 相关配置
     */
    val config: CFG

    /**
     * Map 相关操作，针对不同模块的 map 操作可指定不同的名称用于区分
     */
    fun withMap(name: String = "_default"): LightMapOption

    /**
     * List 相关操作 ， 针对不同模块的 list 操作可指定不同的名称用于区分
     */
    fun withList(name: String = "_default"): LightListOption

    /**
     * Set 相关操作 ， 针对不同模块的 set 操作可指定不同的名称用于区分
     */
    fun withSet(name: String = "_default"): LightSetOption
}
