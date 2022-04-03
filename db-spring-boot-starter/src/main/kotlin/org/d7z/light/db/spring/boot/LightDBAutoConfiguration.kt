package org.d7z.light.db.spring.boot

import org.d7z.light.db.api.LightDB
import org.d7z.light.db.jedis.JedisLightDB
import org.d7z.light.db.jedis.LightJedisPool
import org.d7z.light.db.memory.MemoryLightDB
import org.d7z.objects.format.ObjectFormatConfiguration
import org.d7z.objects.format.ObjectFormatContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.AutoConfigureAfter
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import redis.clients.jedis.Jedis
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig

@Configuration
@EnableConfigurationProperties(
    LightDBConfigurationProperties::class,
)
@AutoConfigureAfter(ObjectFormatConfiguration::class)
open class LightDBAutoConfiguration(
    private val applicationContext: ApplicationContext,
) {
    @Autowired
    private lateinit var configuration: LightDBConfigurationProperties

    @Bean
    open fun lightDB(
        objectFormatContext: ObjectFormatContext,
    ): LightDB {
        return when (configuration.mode) {
            LightDBConfigurationProperties.LightDBMode.MEMORY -> MemoryLightDB(configuration.memory.refreshTime)
            LightDBConfigurationProperties.LightDBMode.JEDIS -> lightJedisDB(configuration, objectFormatContext)
        }
    }

    private fun lightJedisDB(
        configuration: LightDBConfigurationProperties,
        objectFormatContext: ObjectFormatContext,

    ): LightDB {
        val jedisConfig = configuration.jedis
        val jedisPoolConfig = JedisPoolConfig().apply {
            maxIdle = jedisConfig.maxIdle
            maxTotal = jedisConfig.maxTotal
            minIdle = jedisConfig.minIdle
        }
        val lightJedisPool = object : LightJedisPool {
            val jedisPool by lazy {
                JedisPool(
                    jedisPoolConfig,
                    jedisConfig.host,
                    jedisConfig.port,
                    jedisConfig.timeout,
                    jedisConfig.password,
                    jedisConfig.db
                )
            }
            override val resource: Jedis
                get() = jedisPool.resource

            override fun close() {
                jedisPool.close()
            }
        }
        return JedisLightDB(
            header = jedisConfig.namespace,
            pool = lightJedisPool,
            dataCovert = objectFormatContext,
            cache = jedisConfig.contextCache
        )
    }
}
