package org.d7z.light.db.memory.structs.list

import org.d7z.light.db.memory.utils.DestroyContainer
import java.util.Vector
import kotlin.reflect.KClass

data class ListDataContainer<T : Any>(
    val type: KClass<T>,
    val data: Vector<T>,
) : DestroyContainer.DestroyDataContainer {
    override val destroy: Boolean
        get() = data.isEmpty()

    override fun clear() {
        data.clear()
    }
}
