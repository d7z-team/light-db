package edgn.lightdb.api

/**
 * LightDB 异常集合
 */
open class LightDBException(message: String) : RuntimeException(message)

/**
 * 实例过期异常
 */
class DestroyException(message: String) : LightDBException(message)

/**
 * 选项不被支持
 */
class OptionNotSupportException(message: String) : LightDBException(message)
