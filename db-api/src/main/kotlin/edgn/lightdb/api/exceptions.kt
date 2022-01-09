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
 * MetaData不被支持 , 未实现对应 MetaData
 */
class MetaDataNotSupportException(message: String) : LightDBException(message)
