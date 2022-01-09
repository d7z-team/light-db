package edgn.lightdb.test

import edgn.lightdb.api.LightDB
import edgn.lightdb.test.structs.list.ListTest
import edgn.lightdb.test.tests.LoggerTest
import edgn.lightdb.test.tests.StaticTest
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.slf4j.Marker
import org.slf4j.MarkerFactory
import java.io.Closeable
import java.util.ServiceLoader
import kotlin.reflect.KClass
import kotlin.reflect.full.functions
import kotlin.reflect.full.primaryConstructor

/**
 * 此模块用于功能性测试，保证各个模块功能一致
 */
object LightDBTest {
    @JvmStatic
    fun main(args: Array<String>) {
        val dbs = ServiceLoader.load(LightDB::class.java).toList()
        dbs.forEach {
            test(it)
        }
    }

    private val logger = LoggerFactory.getLogger(LightDBTest::class.java)

    private fun test(db: LightDB) {
        val marker = MarkerFactory.getMarker(db.name)
        logger.info(marker, "开始测试 {} 模块.", db.name)
        val tests = listOf<KClass<*>>(ListTest::class) // 支持的测试用例
        for (test in tests) {
            val testOneFun = testOneFun(db, test)
            logger.info(
                "功能测试 ${test.simpleName} 结果 -> 通过：{}，总数：{}，通过率：{} % .",
                testOneFun.first,
                testOneFun.second,
                testOneFun.first * 100 / testOneFun.second
            )
        }
        logger.info("模块 {} 测试结束 . ", db.name)
        db.close()
    }

    private fun testOneFun(db: LightDB, test: KClass<*>): Pair<Int, Int> {
        var resultA = 0
        var resultB = 0
        val testObj = test.primaryConstructor!!.call(db)
        val logger: Logger
        val marker: Marker

        if (testObj is LoggerTest) {
            logger = testObj.logger
            marker = testObj.marker
        } else {
            logger = LoggerFactory.getLogger(test.java)
            marker = MarkerFactory.getMarker(db.name)
        }
        if (testObj is StaticTest) {
            logger.info(marker, "测试功能 - {}.", testObj.name)
        }

        test.functions.filter { it.visibility != null }
            .filter { it.name.startsWith("test").or(it.name.endsWith("test")) }.forEach {
                val funcName = it.name
                logger.info(marker, "测试 \"{}\" 方法.", funcName)
                resultB++
                try {
                    it.call(testObj)
                    logger.info("测试用例通过.😀")
                    resultA++
                } catch (e: Exception) {
                    logger.error(marker, "测试方法 $funcName 执行时发生错误.", e)
                }
            }
        if (testObj is Closeable) {
            try {
                testObj.close()
            } catch (_: Exception) {
            }
        }
        return Pair(resultA, resultB)
    }
}
