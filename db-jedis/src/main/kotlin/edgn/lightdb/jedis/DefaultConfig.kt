package edgn.lightdb.jedis

import edgn.lightdb.api.utils.ConfigLoader
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import redis.clients.jedis.Protocol
import redis.clients.jedis.util.Pool

object DefaultConfig {
    private val configLoader = ConfigLoader(
        System.getProperties()
            .map {
                Pair(it.key.toString(), it.value.toString())
            }
            .toMap()
    )

    /**
     * 默认的 Jedis 连接池配置，仅用于测试,生产环境下请自行配置以满足业务需求
     */
    fun defaultJedisPool(): Pool<Jedis> {
        val jedisPoolConfig = JedisPoolConfig().apply {
            maxIdle = 16
            maxTotal = 32
        }
        val host = configLoader.getString("jedis.host", Protocol.DEFAULT_HOST)
        val port = configLoader.getInt("jedis.port", Protocol.DEFAULT_PORT)
        val timeout = configLoader.getInt("jedis.timeout", Protocol.DEFAULT_TIMEOUT)
        val password = configLoader.getString("jedis.password", "")
        val db = configLoader.getInt("jedis.db", Protocol.DEFAULT_DATABASE)
        return JedisPool(jedisPoolConfig, host, port, timeout, password, db)
    }
}
