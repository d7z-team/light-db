package edgn.lightdb.memory.internal.universal

import java.io.Closeable
import java.util.concurrent.ConcurrentHashMap

class UniversalMemoryGroup<T : Any> : Closeable {
    private val dataMap = ConcurrentHashMap<String, T>()
    override fun close() {
    }
}
