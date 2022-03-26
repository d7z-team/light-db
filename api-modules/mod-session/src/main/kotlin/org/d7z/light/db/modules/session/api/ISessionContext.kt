package org.d7z.light.db.modules.session.api

import java.time.LocalDateTime
import java.util.Optional
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.jvm.jvmErasure

/**
 * Session 会话实例
 */
interface ISessionContext {
    /**
     * 会话 ID
     */
    val sessionToken: String

    /**
     * 配置会话存活时间 (秒),如果配置小于0则立即过期
     */
    var survivalTime: Long

    /**
     * 创建时间
     */
    val createTime: LocalDateTime

    /**
     * 修改时间
     */
    val updateTime: LocalDateTime

    /**
     * 会话绑定
     * @param thisRef T 被绑定实例
     * @param property KProperty<*> 绑定的字段
     * @return V 绑定的返回
     */
    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any?, V : Any> getValue(thisRef: T, property: KProperty<*>): V {
        return getConfig(property.name, property.returnType.jvmErasure).orElseThrow {
            throw SessionException("未找到名为 ${property.name} 的数据.")
        } as V
    }

    @Suppress("UNCHECKED_CAST")
    operator fun <T : Any?, V : Any> setValue(
        thisRef: T,
        property: KProperty<*>,
        value: V,
    ) {
        putConfig(property.name, property.returnType.jvmErasure as KClass<V>, value)
    }

    /**
     *
     * 会话数据绑定
     *
     * @param type KClass<V> 绑定字段类型
     * @param name String 绑定名称，如果为空则为 field 名称
     * @param default Function0<V>  默认值
     * @return ReadWriteProperty<T, V> 绑定实例
     */
    fun <T : Any?, V : Any> config(
        type: KClass<V>,
        name: String = "",
        default: () -> V = { throw SessionException("未找到名为 $name 的数据.") },
    ): ReadWriteProperty<T, V> {
        return object : ReadWriteProperty<T, V> {
            override fun getValue(thisRef: T, property: KProperty<*>): V {
                return getConfig(name.ifBlank { property.name }, type).orElseGet(default)
            }

            override fun setValue(thisRef: T, property: KProperty<*>, value: V) {
                putConfig(name.ifBlank { property.name }, type, value)
            }
        }
    }

    /**
     * 获取配置
     *
     * @param name String 配置名称
     * @param default T 如果不存在的默认值
     * @return T 配置数据或默认值
     */
    fun <T : Any> getConfig(
        name: String,
        type: KClass<T>,
        default: T,
    ): T = getConfig<T>(name, type).orElse(default)

    /**
     * 获取配置
     *
     * @param name String 配置名称
     * @return T 配置数据或Empty
     */
    fun <T : Any> getConfig(
        name: String,
        type: KClass<T>,
    ): Optional<T>

    /**
     * 写入配置
     *
     * @param name String 配置名称
     * @param data T 配置内容
     */
    fun <T : Any> putConfig(
        name: String,
        type: KClass<T>,
        data: T,
    )

    /**
     * 刷新剩余过期时间
     */
    fun refresh()
}
