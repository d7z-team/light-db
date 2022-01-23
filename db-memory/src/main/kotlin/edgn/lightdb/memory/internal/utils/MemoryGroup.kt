package edgn.lightdb.memory.internal.utils

import java.util.Optional
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantReadWriteLock

class MemoryGroup<T : IDataModules> : Clear {
    private val lock = ReentrantReadWriteLock()
    private val readLock = lock.readLock()
    private val writeLock = lock.writeLock()
    private val dataMap = ConcurrentHashMap<String, T>()

    private fun <T : Any> Lock.withLock(block: () -> T): T {
        lock()
        try {
            val res = block()
            unlock()
            return res
        } catch (e: Exception) {
            unlock()
            throw e
        }
    }

    val size: Int
        get() = readLock.withLock {
            dataMap.size
        }

    fun get(key: String): Optional<T> = readLock.withLock {
        Optional.ofNullable(dataMap[key])
            .filter { it.available && it.isNotEmpty }
    }

    fun getOrCreate(key: String, create: () -> T): T = writeLock.withLock {
        dataMap[key]?.let {
            if (it.isNotEmpty.not()) {
                dataMap.remove(key)
            }
        }
        dataMap.getOrPut(key, create)
    }

    fun remove(key: String): Optional<T> = writeLock.withLock {
        Optional.ofNullable(dataMap.remove(key))
    }

    fun exists(key: String): Boolean = readLock.withLock {
        val get = get(key)
        get.isPresent && get.get().isNotEmpty
    }

    fun removeIf(filter: (T) -> Boolean) = writeLock.withLock {
        val keys = dataMap.keys()
        for (key in keys) {
            if (filter(dataMap[key] ?: continue)) {
                dataMap.remove(key)
            }
        }
    }

    fun refresh() {
        removeIf {
            it.available.not() || it.isNotEmpty.not()
        }
    }

    override fun clear() = writeLock.withLock {
        dataMap.values.forEach { it.clear() }
        dataMap.clear()
    }
}
