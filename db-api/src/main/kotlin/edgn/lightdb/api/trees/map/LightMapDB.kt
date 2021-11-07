package edgn.lightdb.api.trees.map

import edgn.lightdb.api.trees.DataTreeOption
import java.util.Optional
import kotlin.reflect.KClass

/**
 * 类 Map 实例
 */
interface LightMapDB : DataTreeOption {
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<LightMap<V>>

    /**
     *
     * 获取实例,如果不存在则创建
     *
     * @param created Function1<LightMap<V>, Unit> 当实例创建时执行的方法
     */
    fun <V : Any> getOrCreate(
        key: String,
        wrap: KClass<V>,
        created: (LightMap<V>) -> Unit = {}
    ): LightMap<V>
}
