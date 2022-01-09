package edgn.lightdb.test.tests

import edgn.lightdb.api.LightDB

interface StaticTest {
    val name: String
        get() = javaClass.name
    val db: LightDB
}
