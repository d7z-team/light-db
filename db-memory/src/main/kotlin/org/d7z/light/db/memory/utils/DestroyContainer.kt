package org.d7z.light.db.memory.utils

import java.util.Optional
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantReadWriteLock

/**
 * 带自动回收的容器
 */
class DestroyContainer<T : Any> {
    // 读写锁对象
    private val lock = ReentrantReadWriteLock()
    private val readLock = lock.readLock()
    private val writeLock = lock.writeLock()

    // 数据存放容器
    private val container = HashMap<String, DataContainer<T>>()

    fun getContainer(key: String): Optional<T> = readLock.withLock {
        Optional.ofNullable(container[key]).filter {
            it.isAvailable()
        }.map { it.data }
    }

    fun getOrCreate(key: String, function: () -> T): T = writeLock.withLock {
        var data = container.getOrElse(key) {
            DataContainer(data = function())
        }
        val child = data.data
        if (data.isAvailable().not()) {
            // 此数据生命周期已结束
            container.remove(key)
            if (child is DestroyDataContainer) {
                child.clear() // 销毁旧的数据
            }
            data = DataContainer(data = function()) // 覆盖新的的数据
        }
        container[key] = data
        data.data
    }

    /**
     * 当前时间（秒）
     */
    private val currentSecond: Long
        get() = System.currentTimeMillis() / 1000

    /**
     * 数据包装容器
     * @param T : Any 实际类型
     * @property timeout AtomicLong 目标过期时间
     * @property data T 容器存储
     * @property validTag AtomicBoolean 数据有效标记
     * @constructor
     */
    data class DataContainer<T : Any>(
        val validTag: AtomicBoolean = AtomicBoolean(true),
        val timeout: AtomicLong = AtomicLong(-1),
        val data: T,
    )

    fun exists(key: String): Boolean {
        return container[key]?.isAvailable() ?: false
    }

    fun getTimeout(key: String): Long {
        return container[key]?.timeout?.get() ?: throw NoSuchElementException("key '$key' not found.")
    }

    fun setTimeout(key: String, second: Long): Boolean {
        return Optional.ofNullable(container[key]).filter { it.isAvailable() }.map {
            if (second < 0) {
                it.timeout.set(-1) // 清除过期时间
            } else if (second == 0L) {
                it.validTag.set(false) // 立即过期
                it.timeout.set(currentSecond)
            } else {
                it.timeout.set(currentSecond + second)
            }
            true
        }.equals(false)
    }

    /**
     * 带数据销毁的容器包装
     */
    interface DestroyDataContainer {
        val destroy: Boolean
        fun clear()
    }

    /**
     * 此数据是否可用
     * @receiver DataContainer<T>
     * @return Boolean
     */
    private fun <T : Any> DataContainer<T>.isAvailable(): Boolean {
        val timeout = timeout.get()
        return validTag.get() && if (data is DestroyDataContainer) {
            data.destroy.not()
        } else {
            true
        } && (timeout < 0 || timeout > currentSecond)
    }

    private inline fun <R : Any?> Lock.withLock(function: () -> R): R {
        return try {
            this.lock()
            val res = function()
            this.unlock()
            res
        } catch (e: Exception) {
            this.unlock()
            throw e
        }
    }

    fun refresh() = writeLock.withLock {
        container.filter { it.value.isAvailable().not() }.forEach { (t, u) ->
            container.remove(t)
            if (u.data is DestroyDataContainer) {
                u.data.clear()
            }
        }
    }

    fun clear() = writeLock.withLock {
        val iterator = container.iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            iterator.remove()
            val data = next.value.data
            if (data is DestroyDataContainer) {
                data.clear()
            }
        }
    }
}
