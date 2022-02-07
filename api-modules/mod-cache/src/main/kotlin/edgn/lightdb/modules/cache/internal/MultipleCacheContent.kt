package edgn.lightdb.modules.cache.internal

import edgn.lightdb.api.structs.map.LightMapGroup
import edgn.lightdb.modules.cache.api.IMultipleCacheContent
import edgn.lightdb.modules.cache.api.IMultipleKey
import kotlin.reflect.KClass

class MultipleCacheContent<V : Any>(
    override val group: String,
    private val valueType: KClass<V>,
    private val cacheContainer: LightMapGroup,
) : IMultipleCacheContent<V>, SingleCacheContent<IMultipleKey, V>(group, IMultipleKey::class, valueType, cacheContainer)
