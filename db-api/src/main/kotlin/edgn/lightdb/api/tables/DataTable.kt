package edgn.lightdb.api.tables

import java.util.Optional
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

/**
 * 实例包装器
 */
interface DataTable<V : Any> {
    /**
     * 实例名称
     */
    val key: String

    /**
     * 持有实例是否可用 (对外可见)
     *
     * 当持有对象被销毁或过期后，此值为 `false`
     */
    val available: Boolean

    /**
     * 持有实例包装的数据类型
     */
    val clazz: KClass<V>

    /**
     * 获取实例，如果实例已过期或销毁则返回 NULL
     */
    fun get(): Optional<out DataValue<V>>

    /**
     * 获取过期时间
     *
     *注意：当剩余小于 0 则表示此实例已过期，
     * 但请注意,等于0不一定是过期，可能是数值过小导致精度丢失
     */
    fun expire(unit: TimeUnit = TimeUnit.MILLISECONDS): Long

    /**
     * 设置实例过期时间，当实例已过期或销毁时，此方法将出现异常
     */
    fun expire(timeout: Long, unit: TimeUnit)
}
