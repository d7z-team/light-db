package org.d7z.light.db.api.utils

import org.d7z.light.db.api.structs.list.LightList
import org.d7z.light.db.api.structs.list.LightListGroup
import org.d7z.light.db.api.structs.map.LightMap
import org.d7z.light.db.api.structs.map.LightMapGroup
import org.d7z.light.db.api.structs.set.LightSet
import org.d7z.light.db.api.structs.set.LightSetGroup

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
