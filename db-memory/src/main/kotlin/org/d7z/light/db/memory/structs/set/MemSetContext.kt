package org.d7z.light.db.memory.structs.set

import org.d7z.light.db.api.struct.LightSet
import org.d7z.light.db.api.struct.SetContext
import org.d7z.light.db.memory.utils.DestroyContainer
import java.util.Optional
import java.util.concurrent.ConcurrentSkipListSet
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * 基于内存实现的 Light List
 */
class MemSetContext : SetContext {
    val container = DestroyContainer<SetDataContainer<Any>>()

    @Suppress("UNCHECKED_CAST")
    fun <V : Any> getContent(key: String, wrap: KClass<V>): Optional<SetDataContainer<V>> {
        return container.getContainer(key).filter { it.type.isSubclassOf(wrap) } as Optional<SetDataContainer<V>>
    }

    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightSet<V>> {
        return getContent(key, wrap).map { MemLightSet(key, wrap, this) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>, create: () -> V): LightSet<V> {
        return container.getOrCreate(key) {
            val data = ConcurrentSkipListSet<V>()
            data.add(create())
            SetDataContainer(wrap, data) as SetDataContainer<Any>
        }.let {
            if (it.type.isSubclassOf(wrap).not()) {
                throw ClassCastException("$it.type not SubclassOf $wrap .")
            }
            MemLightSet(key, wrap, this)
        }
    }

    override fun exists(key: String): Boolean {
        return container.exists(key)
    }

    override fun getTimeout(key: String): Long {
        return container.getTimeout(key)
    }

    override fun setTimeout(key: String, second: Long): Boolean {
        return container.setTimeout(key, second)
    }
}
