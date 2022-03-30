package org.d7z.light.db.memory.structs.set

import org.d7z.light.db.memory.utils.DestroyContainer
import kotlin.reflect.KClass

class SetDataContainer<T : Any>(
    val type: KClass<T>,
    val data: MutableMap<T, Any>,
) : DestroyContainer.DestroyDataContainer {
    override val destroy: Boolean
        get() = data.isEmpty()

    override fun clear() {
        data.clear()
    }
}
