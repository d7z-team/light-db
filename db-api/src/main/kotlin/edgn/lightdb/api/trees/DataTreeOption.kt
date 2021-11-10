package edgn.lightdb.api.trees

import java.util.Optional
import kotlin.reflect.KClass

/**
 * 各个模块基础函数
 */
interface DataTreeOption {
    /**
     *
     * 根据 key 获取实例.如果实例不存在则返回空串，
     * 注意，所有相同 key 返回的均为同一对象的引用
     *
     * @param key String key
     * @param wrap KClass<V> 对应实例的值对象类型
     * @return Optional<DataTree<V>> 实例的包装
     */
    fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out DataTree<V>>

    /**
     * 根据 key 创建实例.如果实例存在则返回false
     *
     * @param key String key
     * @param wrap KClass<out Any> 对应实例的值对象类型
     * @return Boolean 如果创建成功则返回 true
     */
    fun create(
        key: String,
        wrap: KClass<out Any>
    ): Boolean

    /**
     * 销毁实例本身，如果实例不存在则返回false
     * 注意，销毁后所有与此 key 相关的实例均会失效且抛出异常,
     *
     */
    fun drop(key: String): Boolean

    /**
     * 判断此key的实例是否存在
     */
    fun exists(key: String): Boolean
}
