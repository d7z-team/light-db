package edgn.lightdb.api.utils

import edgn.lightdb.api.structs.list.LightList
import edgn.lightdb.api.structs.list.LightListGroup
import edgn.lightdb.api.structs.map.LightMap
import edgn.lightdb.api.structs.map.LightMapGroup
import edgn.lightdb.api.structs.set.LightSet
import edgn.lightdb.api.structs.set.LightSetGroup

/**
 *  kotlin 语法优化
 *
 *  请查看原始代码了解函数使用方法
 */
inline fun <reified V : Any> LightListGroup.getOrCreate(
    key: String
): LightList<V> = this.getOrCreate(key, V::class)

/**
 *  kotlin 语法优化
 *
 *  请查看原始代码了解函数使用方法
 */
inline fun <reified K : Any, reified V : Any> LightMapGroup.getOrCreate(
    key: String,
): LightMap<K, V> = this.getOrCreate(key, K::class, V::class)

/**
 *  kotlin 语法优化
 *
 *  请查看原始代码了解函数使用方法
 */
inline fun <reified V : Any> LightSetGroup.getOrCreate(key: String): LightSet<V> = this.getOrCreate(key, V::class)
