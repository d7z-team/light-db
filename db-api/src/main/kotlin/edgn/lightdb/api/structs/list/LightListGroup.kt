package edgn.lightdb.api.structs.list

import edgn.lightdb.api.structs.DataGroup
import java.util.Optional
import kotlin.reflect.KClass

/**
 * List 数据归组
 */
interface LightListGroup : DataGroup {
    /**
     *
     * 根据 key 和 class 获取对应的 LIST 对象
     *
     * 如果 key 不存在或者 wrap 对象与实际对象不一致则返回 `Optional.empty()`
     *
     */
    fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightList<V>>

    /**
     *
     * 获取现有 List 或创建新的 List 对象
     *
     * 获取已存在的 List 对象，如果 List对象不存在则新建 List 对象
     *
     */
    fun <V : Any> getOrCreate(
        key: String,
        wrap: KClass<V>,
    ): LightList<V>
}
