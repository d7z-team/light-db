package org.d7z.light.db.api.struct

import java.util.Optional
import kotlin.reflect.KClass

interface SetContext : StructContext {
    /**
     * 获取 Set 数据
     *
     *  获取集合下的 Set 数据，如果不存在则返回 ptional.empty()
     */
    fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightSet<V>>

    /**
     *
     * 获取现有 Set 或创建新的 Set 对象
     *
     * 获取已存在的 Set 对象，如果不存在则新建 Set 对象
     *
     */
    fun <V : Any> getOrCreate(
        key: String,
        wrap: KClass<V>,
        create: () -> V,
    ): LightSet<V>
}
