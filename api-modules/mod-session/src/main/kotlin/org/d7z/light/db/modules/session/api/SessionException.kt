package org.d7z.light.db.modules.session.api

/**
 * Session 异常
 * @constructor
 */
class SessionException(msg: String, e: Exception? = null) : RuntimeException(msg, e)
