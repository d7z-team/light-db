package org.d7z.light.db.memory.structs.map

import org.d7z.light.db.memory.utils.DestroyContainer
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass

data class MapDataContainer<K : Any, V : Any>(
    val keyType: KClass<K>,
    val valueType: KClass<V>,
    val data: ConcurrentHashMap<K, V>,
) : DestroyContainer.DestroyDataContainer {
    override val destroy: Boolean
        get() = data.isEmpty()

    override fun clear() {
        data.clear()
    }
}
