package org.d7z.light.db.memory.structs.map

import org.d7z.light.db.api.error.DestroyException
import org.d7z.light.db.api.struct.LightMap
import java.util.Optional
import kotlin.reflect.KClass

class MemLightMap<K : Any, V : Any>(
    private val name: String,
    override val keyType: KClass<K>,
    override val valueType: KClass<V>,
    private val context: MemMapContext,
) : LightMap<K, V> {

    private fun <T : Any?> execute(function: MapDataContainer<K, V>.(MapDataContainer<K, V>) -> T): T {
        return context.getContent(name, keyType, valueType).map {
            function(it, it)
        }.orElseThrow { throw DestroyException("未找到名为 $name 的 Map 集合.") }
    }

    override fun put(key: K, value: V): Optional<V> = execute {
        Optional.ofNullable(it.data.put(key, value))
    }

    override fun putIfAbsent(key: K, value: V): V = execute {
        it.data.putIfAbsent(key, value) ?: value
    }

    override fun compareAndSwap(key: K, oldValue: V, newValue: V): Boolean = execute {
        it.data.merge(
            key, newValue
        ) { v1, _ ->
            if (v1 == oldValue) {
                newValue
            } else {
                oldValue
            }
        } == newValue
    }

    override fun containsKey(key: K): Boolean = execute {
        it.data.containsKey(key)
    }

    override fun get(key: K): Optional<V> = execute {
        Optional.ofNullable(it.data[key])
    }

    override fun removeKey(key: K): Optional<V> = execute {
        Optional.ofNullable(data.remove(key))
    }

    override fun keys(): Iterator<K> {
        return object : Iterator<K> {
            private val keys = execute { it.data.keys().asIterator() }
            override fun hasNext(): Boolean {
                return keys.hasNext()
            }

            override fun next(): K {
                return keys.next()
            }
        }
    }

    override val size: Long
        get() = execute {
            data.size.toLong()
        }
}
