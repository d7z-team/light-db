package org.d7z.light.db.modules.session

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test

internal class LightSessionTest {

    @Test
    fun findSessionGroupContext() {
        val build = LightSession.Builder().build()
        val newSession = build.findSessionGroupContext().newSession()
        println(newSession.sessionId)
        println(newSession.updateTime)
        val find = build.findSessionGroupContext()
        val querySession = find.querySession(newSession.sessionId)
        assertEquals(querySession.get().createTime, newSession.createTime)
        find.destroy(newSession.sessionId)
        assertFalse(find.querySession(newSession.sessionId).isPresent)
    }
}
