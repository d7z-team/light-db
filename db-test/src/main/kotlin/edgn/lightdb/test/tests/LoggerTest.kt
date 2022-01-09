package edgn.lightdb.test.tests

import org.slf4j.Logger
import org.slf4j.Marker
import org.slf4j.MarkerFactory

interface LoggerTest : StaticTest {

    val logger: Logger
    val marker: Marker
        get() = MarkerFactory.getMarker(db::class.simpleName)
}
