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
     * 此 LightDB 配置
     */
    val config: CFG

    /**
     * Map 相关操作
     */
    fun withMap(name: String = "_default"): LightMapOption

    /**
     * List 相关操作
     */
    fun withList(name: String = "_default"): LightListOption

    /**
     * Set 相关操作
     */
    fun withSet(name: String = "_default"): LightSetOption
}
