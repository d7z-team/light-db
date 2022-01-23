package edgn.lightdb.jedis.internal.utils

import org.json.JSONObject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class JsonDataCovertUtilsTest {

    data class TestA(
        val data: String,
        val date: String
    )

    @Test
    fun format() {
        val format = JsonDataCovertUtils()
        assertEquals(format.format("data"), "data")
        assertEquals(format.format("data", CharSequence::class), "data")
        val data = TestA("date", "now")
        assertEquals(JSONObject(format.format(data)).getJSONObject("data").keySet(), JSONObject(data).keySet())
    }

    @Test
    fun reduce() {
        val reduce = JsonDataCovertUtils()
        val data = TestA("date", "now")
        val json = reduce.format(data)
        assertEquals(reduce.reduce(json, TestA::class), data)
        assertEquals(reduce.reduce("String Test", String::class), "String Test")
    }

    @Test
    fun checkFormat() {
        val check = JsonDataCovertUtils()
        val data = TestA("date", "now")
        val json = check.format(data)
        assertTrue(check.checkFormat(json, TestA::class))
        assertTrue(check.checkFormat("data", String::class))
    }
}
