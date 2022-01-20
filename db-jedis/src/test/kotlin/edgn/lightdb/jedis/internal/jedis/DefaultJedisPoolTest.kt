package edgn.lightdb.jedis.internal.jedis

import org.junit.jupiter.api.Test

internal class DefaultJedisPoolTest {
    @Test
    fun testJedis() {
        System.setProperty("jedis.password", "redis-password")
        val pool = DefaultJedisPool()
        pool.session {
            println(
//                redis.call('hset' , KEYS[1], KEYS[2], ARGV[2])

                it.eval(
                    """
                    if redis.call('llen' , KEYS[1]) < tonumber(ARGV[1]) then
                       return nil
                    end
                    local internal_data = redis.call('lindex' , KEYS[1], ARGV[1])
                    local internal_tag = '_light_db_internal_del_tag' .. ARGV[1]
                    redis.call('lset' , KEYS[1] ,ARGV[1], internal_tag)
                    redis.call('lrem', KEYS[1], 0, internal_tag)
                    return internal_data
                    """.trimIndent(),
                    1, "list", "112"
                )
            )
        }
        pool.close()
    }
}
