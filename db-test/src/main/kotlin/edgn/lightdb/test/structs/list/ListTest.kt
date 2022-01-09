package edgn.lightdb.test.structs.list

import edgn.lightdb.api.LightDB
import edgn.lightdb.test.tests.LoggerTest
import org.slf4j.LoggerFactory

/**
 * List 相关测试方案
 */
class ListTest(
    override val db: LightDB
) : LoggerTest {
    override val logger = LoggerFactory.getLogger(LoggerTest::class.java)

    fun testOne() {
        logger.info("Test")
    }
}
