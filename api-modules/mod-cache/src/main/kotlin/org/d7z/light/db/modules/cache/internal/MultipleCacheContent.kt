package org.d7z.light.db.modules.cache.internal

import org.d7z.light.db.api.struct.MapContext
import org.d7z.light.db.modules.cache.api.IMultipleCacheContent
import org.d7z.light.db.modules.cache.api.IMultipleKey
import kotlin.reflect.KClass

class MultipleCacheContent<V : Any>(
    override val group: String,
    valueType: KClass<V>,
    cacheContainer: MapContext,
) : IMultipleCacheContent<V>, SingleCacheContent<IMultipleKey, V>(group, IMultipleKey::class, valueType, cacheContainer)
