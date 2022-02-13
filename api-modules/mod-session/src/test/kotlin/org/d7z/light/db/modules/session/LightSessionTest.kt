package org.d7z.light.db.modules.session

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class LightSessionTest {

    @Test
    fun findSessionGroupContext() {
        val build = LightSession.Builder().build()
        val newSession = build.findSessionGroupContext().newSession()
        println(newSession.sessionId)
        println(newSession.updateTime)
        val findSessionGroupContext = build.findSessionGroupContext()
        val querySession = findSessionGroupContext.querySession(newSession.sessionId)
        assertEquals(querySession.get().createTime, newSession.createTime)
        findSessionGroupContext.destroy(newSession.sessionId)
    }
}
