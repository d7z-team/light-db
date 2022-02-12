package org.d7z.light.db.api.structs.map

import org.d7z.light.db.api.structs.DataGroup
import java.util.Optional
import kotlin.reflect.KClass

/**
 * Map 数据集合
 */
interface LightMapGroup : DataGroup {
    /**
     * 获取 map 数据
     *
     *  获取集合下的 map 数据，如果不存在则返回 Optional.empty()
     */
    fun <K : Any, V : Any> get(
        key: String,
        keyType: KClass<K>,
        valueType: KClass<V>
    ): Optional<out LightMap<K, V>>

    /**
     *
     * 获取现有 Map 或创建新的 Map 对象
     *
     * 获取已存在的 Map 对象，如果不存在则新建 Map 对象
     *
     */
    fun <K : Any, V : Any> getOrCreate(
        key: String,
        keyType: KClass<K>,
        valueType: KClass<V>
    ): LightMap<K, V>
}
