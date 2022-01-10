package edgn.lightdb.api.utils

import edgn.lightdb.api.MetaDataNotSupportException
import edgn.lightdb.api.structs.LightDBData
import edgn.lightdb.api.structs.MetaData
import java.util.Optional
import kotlin.reflect.KClass
import kotlin.reflect.full.isSuperclassOf

/**
 * 智能转换 Meta 对象
 */
inline fun <reified T : MetaData, V : Any> LightDBData<V>.metaOrNull(type: KClass<T>): Optional<T> {
    if (this.meta::class.isSuperclassOf(type) && this is T) {
        return Optional.of(this)
    }
    return Optional.empty()
}

/**
 * 智能转换 Meta 对象
 */
inline fun <reified T : MetaData, V : Any> LightDBData<V>.metaOrNull(): Optional<T> {
    return this.metaOrNull(T::class)
}

/**
 * 智能转换 Meta 对象
 */
inline fun <reified T : MetaData, V : Any> LightDBData<V>.metaOrThrows(type: KClass<T>): T {
    if (this.meta::class.isSuperclassOf(type) && this is T) {
        return this
    } else {
        throw MetaDataNotSupportException("不支持此 MetaData($type), 目标 MetaData(${this::class}).")
    }
}

/**
 * 智能转换 Meta 对象
 */
inline fun <reified T : MetaData, V : Any> LightDBData<V>.metaOrThrows(): T {
    return this.metaOrThrows(T::class)
}
