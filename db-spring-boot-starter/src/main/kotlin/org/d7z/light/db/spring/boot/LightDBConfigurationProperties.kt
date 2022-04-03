package org.d7z.light.db.spring.boot

import org.apache.commons.pool2.impl.GenericObjectPoolConfig
import org.springframework.boot.context.properties.ConfigurationProperties
import redis.clients.jedis.Protocol

@ConfigurationProperties("spring.data.light-db")
class LightDBConfigurationProperties {
    /**
     * LightDB 后端实现
     */
    var mode: LightDBMode = LightDBMode.MEMORY

    /**
     * jedis 配置
     */
    val jedis: JedisProperties = JedisProperties()

    /**
     * 内存配置
     */
    val memory: MemoryProperties = MemoryProperties()

    enum class LightDBMode {
        MEMORY, JEDIS
    }

    class JedisProperties {
        var maxIdle: Int = GenericObjectPoolConfig.DEFAULT_MAX_TOTAL
        var maxTotal: Int = GenericObjectPoolConfig.DEFAULT_MAX_TOTAL
        var minIdle: Int = GenericObjectPoolConfig.DEFAULT_MIN_IDLE
        var host: String = Protocol.DEFAULT_HOST
        var port: Int = Protocol.DEFAULT_PORT
        var password: String? = null
        var timeout: Int = Protocol.DEFAULT_TIMEOUT
        var db: Int = Protocol.DEFAULT_DATABASE
        var contextCache: Boolean = false
        var namespace: String = ""
    }

    class MemoryProperties {
        /**
         * 内部数据刷新时间
         */
        var refreshTime: Long = 60
    }
}
