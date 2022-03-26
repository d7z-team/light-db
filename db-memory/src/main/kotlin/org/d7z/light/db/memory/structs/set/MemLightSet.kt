package org.d7z.light.db.memory.structs.set

import org.d7z.light.db.api.error.DestroyException
import org.d7z.light.db.api.struct.LightSet
import kotlin.reflect.KClass

class MemLightSet<V : Any>(
    private val name: String,
    override val valueType: KClass<V>,
    private val context: MemSetContext,
) : LightSet<V> {
    private fun <T : Any?> execute(function: SetDataContainer<V>.(SetDataContainer<V>) -> T): T {
        return context.getContent(name, valueType).map {
            function(it, it)
        }.orElseThrow { throw DestroyException("未找到名为 $name 的 Set 集合.") }
    }

    override fun add(data: V): Boolean = execute {
        it.data.add(data)
    }

    override fun remove(data: V): Boolean = execute {
        it.data.remove(data)
    }

    override fun contains(data: V): Boolean = execute {
        it.data.contains(data)
    }

    override fun values(): Iterator<V> = execute {
        it.data.iterator().iterator()
    }

    override val size: Long
        get() = execute { it.data.size.toLong() }
}
