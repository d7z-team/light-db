package org.d7z.light.db.memory.structs.list

import org.d7z.light.db.api.struct.LightList
import org.d7z.light.db.api.struct.ListContext
import org.d7z.light.db.memory.utils.DestroyContainer
import java.util.Optional
import java.util.Vector
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * 基于内存实现的 Light List
 */
class MemListContext : ListContext {
    val container = DestroyContainer<ListDataContainer<Any>>()

    @Suppress("UNCHECKED_CAST")
    fun <V : Any> getContent(key: String, wrap: KClass<V>): Optional<ListDataContainer<V>> {
        return container.getContainer(key).filter { it.type.isSubclassOf(wrap) } as Optional<ListDataContainer<V>>
    }

    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out LightList<V>> {
        return getContent(key, wrap).map { MemLightList(key, wrap, this) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <V : Any> getOrCreate(key: String, wrap: KClass<V>, create: () -> V): LightList<V> {
        return container.getOrCreate(key) {
            val data = Vector<V>()
            data.add(create())
            ListDataContainer(wrap, data) as ListDataContainer<Any>
        }.let {
            if (it.type.isSubclassOf(wrap).not()) {
                throw ClassCastException("$it.type not SubclassOf $wrap .")
            }
            MemLightList(key, wrap, this)
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
