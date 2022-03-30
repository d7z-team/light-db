package org.d7z.test

import org.d7z.light.db.api.LightDB
import org.d7z.light.db.api.struct.ListContext
import org.d7z.light.db.api.struct.MapContext
import org.d7z.light.db.api.struct.SetContext
import java.util.ServiceLoader

val lightdb = ServiceLoader.load(LightDB::class.java).iterator().asSequence().toList()

fun testLightDB(block: LightDB.(LightDB) -> Unit) {
    lightdb.forEach { block(it, it) }
}

fun testList(name: String = "test", block: ListContext.(ListContext) -> Unit) = testLightDB {
    val withList = it.withList(name)
    block(withList, withList)
}

fun testSet(name: String = "test", block: SetContext.(SetContext) -> Unit) = testLightDB {
    val withSet = it.withSet(name)
    block(withSet, withSet)
}

fun testMap(name: String = "test", block: MapContext.(MapContext) -> Unit) = testLightDB {
    val withMap = it.withMap(name)
    block(withMap, withMap)
}
