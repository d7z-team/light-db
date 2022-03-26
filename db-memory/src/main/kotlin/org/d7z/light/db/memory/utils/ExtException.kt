package org.d7z.light.db.memory.utils

fun <T : Any?> tryOrDefault(def: T, func: () -> T): T {
    return try {
        func()
    } catch (e: Exception) {
        def
    }
}
