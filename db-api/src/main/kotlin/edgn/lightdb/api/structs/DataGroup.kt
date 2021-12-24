package edgn.lightdb.api.structs

import java.util.Optional
import kotlin.reflect.KClass

/**
 * 各个模块基础函数
 */
interface DataGroup {

    /**
     *
     * 根据 key 获取实例.
     * 如果实例不存在则返回空串
     * 其中，所有相同 key 返回的均为同一对象的引用
     *
     * @param key String key
     * @param wrap KClass<V> 对应实例的值对象类型
     * @return Optional<DataTree<V>> 实例的包装
     */
    fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out DataValue<V>>

    /**
     * 根据 key 创建实例
     *
     * 如果实例存在则返回false，
     * 创建成功均返回true，
     *
     * @param key String key
     * @param wrap KClass<out Any> 对应实例的值对象类型
     * @return Boolean 创建结果
     */
    fun <V : Any> getOrCreate(
        key: String,
        wrap: KClass<V>
    ): DataValue<V>

    /**
     * 根据key销毁实例
     *
     * 如果实例不存在则返回false
     * 注意，销毁后所有与此 key 相关的实例均会失效且抛出异常,
     * 在执行销毁时请注意引用
     *
     * 你可以使用实例的 `clear()` 方法清空
     *
     */
    fun drop(key: String): Boolean

    /**
     * 判断此key的实例是否存在
     */
    fun exists(key: String): Boolean
}
