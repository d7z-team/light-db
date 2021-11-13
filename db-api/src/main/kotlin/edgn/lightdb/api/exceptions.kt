package edgn.lightdb.api

/**
 * LightDB 异常集合
 */
open class LightDBException(message: String) : RuntimeException(message)

/**
 * 实例过期异常
 */
class ExpiredException(message: String) : LightDBException(message)

/**
 * 不支持异常
 */
class NotSupportException(message: String) : LightDBException(message)

/**
 * 符合断言
 */
fun assert(value: Boolean, error: Exception) {
    if (value) {
        throw error
    }
}

/**
 * 不符合断言
 */
fun assertNot(value: Boolean, error: Exception) {
    if (value.not()) {
        throw error
    }
}
