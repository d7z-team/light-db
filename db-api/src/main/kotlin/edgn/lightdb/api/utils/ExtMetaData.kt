package edgn.lightdb.api.utils

import edgn.lightdb.api.MetaDataNotSupportException
import edgn.lightdb.api.structs.LightDBData
import edgn.lightdb.api.structs.MetaData
import java.util.Optional
import kotlin.reflect.KClass

/**
 * 智能转换 Meta 对象
 */
inline fun <reified T : MetaData, V : Any> LightDBData<V>.metaOrNull(): Optional<T> {
    if (this.meta is T) {
        return Optional.of(this.meta as T)
    }
    return Optional.empty()
}

/**
 * 智能转换 Meta 对象
 */
inline fun <reified T : MetaData, V : Any> LightDBData<V>.metaOrThrows(type: KClass<T>): T {
    if (this.meta is T) {
        return this.meta as T
    } else {
        throw MetaDataNotSupportException("不支持此 MetaData($type), 目标 MetaData(${this.meta::class}).")
    }
}

/**
 * 智能转换 Meta 对象
 */
inline fun <reified T : MetaData, V : Any> LightDBData<V>.metaOrThrows(): T {
    return this.metaOrThrows(T::class)
}
