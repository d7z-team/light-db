package org.d7z.light.db.memory.structs.list

import org.d7z.light.db.api.error.DestroyException
import org.d7z.light.db.api.struct.LightList
import org.d7z.light.db.memory.utils.tryOrDefault
import java.util.Optional
import kotlin.reflect.KClass

class MemLightList<V : Any>(
    private val name: String,
    override val valueType: KClass<V>,
    private val context: MemListContext,
) : LightList<V> {

    private fun <T : Any?> execute(function: ListDataContainer<V>.(ListDataContainer<V>) -> T): T {
        return context.getContent(name, valueType).map {
            function(it, it)
        }.orElseThrow { throw DestroyException("未找到名为 $name 的 List 集合.") }
    }

    override fun add(element: V): Unit = execute {
        data.add(element)
    }

    override fun add(index: Long, element: V) = execute {
        it.data.add(index.toInt(), element)
    }

    override fun remove(index: Long): Optional<V> = execute {
        tryOrDefault(Optional.empty()) {
            Optional.of(it.data.removeAt(index.toInt()))
        }
    }

    override fun set(index: Long, element: V): V = execute {
        it.data.set(index.toInt(), element)
    }

    override fun get(index: Long): Optional<V> = execute {
        tryOrDefault(Optional.empty()) {
            Optional.ofNullable(
                it.data[index.toInt()]
            )
        }
    }

    override fun indexOf(element: V): Long = execute {
        it.data.indexOf(element).toLong()
    }

    override fun lastIndexOf(element: V): Long = execute {
        it.data.lastIndexOf(element).toLong()
    }

    override val size: Long
        get() = execute { it.data.size.toLong() }
}
