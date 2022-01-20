package edgn.lightdb.jedis.internal.jedis

import org.junit.jupiter.api.Test

internal class DefaultJedisPoolTest {
    @Test
    fun testJedis() {
        System.setProperty("jedis.password", "redis-password")
        val pool = DefaultJedisPool()
        pool.session {
            it.hset("1212", "1212", "1212")
            println(it.hget("1212", "121q2"))
            println(
                it.eval(
                    """
                    if redis.call('hget' , KEYS[1], KEYS[2]) == ARGV[1] then
                        redis.call('hset' , KEYS[1], KEYS[2], ARGV[2])
                        return 1
                    else
                        return 0
                    end
                    """.trimIndent(),
                    2, "test", "test", "12", "13"
                )::class
            )
        }
        pool.close()
    }
}
