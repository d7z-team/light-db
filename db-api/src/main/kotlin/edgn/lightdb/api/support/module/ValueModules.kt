package edgn.lightdb.api.support.module

import edgn.lightdb.api.DestroyException
import java.io.Closeable
import java.util.Optional
import kotlin.reflect.KClass

/**
 * 数据附加模块集合
 *
 */
interface ValueModules : Closeable {

    /**
     * 所有模块符合可用条件
     */
    val available: Boolean

    /**
     *  绑定模块
     *
     */
    fun <B : ValueModule> bind(module: B): B

    /**
     * 解绑模块
     */
    fun unbind(clazz: KClass<out ValueModule>): Boolean

    /**
     * 根据 class 获取对应模块
     *
     * 如果无此模块则返回 Optional.empty() ,
     */
    fun <T : ValueModule> module(option: KClass<T>): Optional<T>

    /**
     * 测试此模块对应的数据集是否可用
     */
    fun <RES : Any> checkAvailable(func: () -> RES): RES {
        return if (available) {
            func()
        } else {
            throw DestroyException("此实例会话已不可用.")
        }
    }
}
