package org.d7z.light.db.api.structs

import java.util.Optional

/**
 * # LightDB 元数据管理
 *
 * 如无必要请勿直接使用 MetaData 接口方法，
 * 可通过扩展方法使用对应 LightDB 依赖的实现
 *
 */
interface MetaData {
    /**
     * ## 获取 meta 元数据
     *
     * 如果配置 key 不被支持则返回 `Optional.empty()`.否则返回对应数据或默认值
     *
     */
    fun get(key: String): Optional<String>

    /**
     *  ## 配置 meta 元数据
     *
     * 如果配置不被支持则返回 `false`
     */
    fun set(key: String, value: String): Boolean
}
