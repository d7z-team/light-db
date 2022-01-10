package edgn.lightdb.memory.internal.utils

import java.io.Closeable
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap

class MemoryGroup<T : IDataModules> : Closeable {
    private val dataMap = ConcurrentHashMap<String, T>()

    fun get(key: String): Optional<T> {
        return Optional.ofNullable(dataMap[key]).filter { it.available }
    }

    fun getOrCreate(key: String, create: () -> T): T {
        return dataMap.getOrPut(key, create)
    }

    fun remove(key: String): Optional<T> {
        return Optional.ofNullable(dataMap.remove(key))
    }

    fun exists(key: String): Boolean {
        return get(key).isPresent
    }

    fun removeIf(filter: (T) -> Boolean) {
        val keys = dataMap.keys()
        for (key in keys) {
            if (filter(dataMap[key] ?: continue)) {
                dataMap.remove(key)
            }
        }
    }

    @Synchronized
    override fun close() {
        dataMap.values.forEach { it.clear() }
        dataMap.clear()
    }
}
