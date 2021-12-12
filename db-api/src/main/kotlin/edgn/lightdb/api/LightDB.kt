package edgn.lightdb.api

import edgn.lightdb.api.tables.list.LightListNamespace
import edgn.lightdb.api.tables.map.LightMapNamespace
import edgn.lightdb.api.tables.set.LightSetNamespace
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
    fun withMap(name: String = "_default"): LightMapNamespace

    /**
     * List 相关操作 ， 针对不同模块的 list 操作可指定不同的名称用于区分
     */
    fun withList(name: String = "_default"): LightListNamespace

    /**
     * Set 相关操作 ， 针对不同模块的 set 操作可指定不同的名称用于区分
     */
    fun withSet(name: String = "_default"): LightSetNamespace
}
