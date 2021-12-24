package edgn.lightdb.memory.internal.universal.mod

import java.io.Closeable
import java.util.Optional
import java.util.concurrent.ConcurrentHashMap

class MemoryGroup<T : IModules> : Closeable {
    private val dataMap = ConcurrentHashMap<String, T>()

    fun get(key: String): Optional<T> {
        return Optional.ofNullable(dataMap[key]).filter { it.modules.available }
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

    override fun close() {
        dataMap.values.forEach { it.clear() }
        dataMap.clear()
    }
}
