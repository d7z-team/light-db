package edgn.lightdb.jedis.internal.jedis

import org.junit.jupiter.api.Test

internal class DefaultJedisPoolTest {
    @Test
    fun testJedis() {
        val pool = DefaultJedisPool()
        pool.session {
            println(
//                redis.call('hset' , KEYS[1], KEYS[2], ARGV[2])

                it.eval(
                    """
                    local key = KEYS[1]
                    local obj = ARGV[1]
                    local items = redis.call('lrange', key, 0, -1)
                    for i =1,#items do
                        if table.remove(items) == obj then
                            return #items 
                        end
                    end 
                    return -1
                    """.trimIndent(),
                    1, "list", "12"
                )
            )
        }
        pool.close()
    }
}
