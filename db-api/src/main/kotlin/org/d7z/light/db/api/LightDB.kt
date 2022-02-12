package org.d7z.light.db.api

import org.d7z.light.db.api.structs.list.LightListGroup
import org.d7z.light.db.api.structs.map.LightMapGroup
import org.d7z.light.db.api.structs.set.LightSetGroup
import java.io.Closeable

/**
 * LightDB 主体
 */
interface LightDB : Closeable {

    /**
     * LightDB实现名称
     */
    val name: String

    /**
     * Map 相关操作，针对不同模块的 map 操作可指定不同的名称用于区分
     *
     * 注意：请勿大量生成不同名字 DataGroup 对象
     */
    fun withMap(name: String = "_default"): LightMapGroup

    /**
     * List 相关操作 ， 针对不同模块的 list 操作可指定不同的名称用于区分
     *
     * 注意：请勿大量生成不同名字 DataGroup 对象
     */
    fun withList(name: String = "_default"): LightListGroup

    /**
     * Set 相关操作 ， 针对不同模块的 set 操作可指定不同的名称用于区分
     *
     * 注意：请勿大量生成不同名字 DataGroup 对象
     */
    fun withSet(name: String = "_default"): LightSetGroup
}
