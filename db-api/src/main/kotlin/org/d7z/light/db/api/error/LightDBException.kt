package org.d7z.light.db.api.error

/**
 * LightDB 异常集合
 */
open class LightDBException @JvmOverloads constructor(message: String, e: Exception? = null) :
    RuntimeException(message, e)
