package edgn.lightdb.api

/**
 * LightDB 异常集合
 */
open class LightDBException @JvmOverloads constructor(message: String, e: Exception? = null) :
    RuntimeException(message, e)

/**
 * 实例过期异常
 */
class DestroyException(message: String) : LightDBException(message)

/**
 * MetaData不被支持 , 未实现对应 MetaData
 */
class MetaDataNotSupportException(message: String) : LightDBException(message)
