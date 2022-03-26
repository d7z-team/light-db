package org.d7z.light.db.api

import org.d7z.light.db.api.struct.ListContext
import org.d7z.light.db.api.struct.MapContext
import org.d7z.light.db.api.struct.SetContext
import java.io.Closeable
import java.util.ServiceLoader
import kotlin.reflect.full.primaryConstructor

/**
 * LightDB 主体
 */
interface LightDB : Closeable {

    /**
     * LightDB实现名称
     */
    val name: String

    /**
     * Map 相关操作，针对不同模块的 map 操作可指定不同的名称用于区分
     *
     * 注意：请勿大量生成不同名字 DataGroup 对象
     */
    fun withMap(name: String = "_default"): MapContext

    /**
     * List 相关操作 ， 针对不同模块的 list 操作可指定不同的名称用于区分
     *
     * 注意：请勿大量生成不同名字 DataGroup 对象
     */
    fun withList(name: String = "_default"): ListContext

    /**
     * Set 相关操作 ， 针对不同模块的 set 操作可指定不同的名称用于区分
     *
     * 注意：请勿大量生成不同名字 DataGroup 对象
     */
    fun withSet(name: String = "_default"): SetContext

    /**
     *  LightDB 内部加载器
     */
    companion object : LightDB {
        private val lazyLightDB: LightDB by lazy {
            loadGlobalLightDB().apply { // 创建销毁回调
                Runtime.getRuntime().addShutdownHook(
                    Thread {
                        this.close()
                    }
                )
            }
        }

        override val name: String
            get() = lazyLightDB.name

        override fun withMap(name: String) = lazyLightDB.withMap(name)

        override fun withList(name: String) = lazyLightDB.withList(name)

        override fun withSet(name: String) = lazyLightDB.withSet(name)

        override fun close() {
            System.err.println("警告，此时不应该调用此函数，此对象生命周期因与 JVM / Classloader 一致.")
            lazyLightDB.close()
        }

        /**
         * 尝试加载全局可用的 LightDB
         */
        private fun loadGlobalLightDB(): LightDB {
            val props = listOf("lightDB.loader", "lightdb.loader", "lightDb.loader", "light-db.loader")
            for (prop in props) {
                try {
                    val property = System.getProperty(prop)
                    if (property != null) {
                        val anyClass = Thread.currentThread().contextClassLoader.loadClass(property).kotlin
                        return anyClass.primaryConstructor!!.call() as LightDB
                    }
                } catch (_: Exception) {
                }
            }
            val spi = ServiceLoader.load(LightDB::class.java)
            return spi.findFirst().orElseThrow {
                RuntimeException("未在类路径下找到可用的 LightDB 实现.")
            }
        }
    }
}
