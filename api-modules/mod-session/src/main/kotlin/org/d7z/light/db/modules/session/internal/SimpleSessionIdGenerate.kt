package org.d7z.light.db.modules.session.internal

import org.d7z.light.db.modules.session.api.ISessionIDGenerate
import org.d7z.light.db.modules.session.api.SessionException
import java.util.Optional
import java.util.UUID
import kotlin.experimental.xor

class SimpleSessionIdGenerate : ISessionIDGenerate {
    override fun generate(name: String): String {
        val header = String.format("%-6s", name).substring(0, 5)
        val session = UUID.randomUUID()
        val seed = (session.leastSignificantBits % Byte.MAX_VALUE).toByte()
        val head = header.toByteArray(Charsets.ISO_8859_1).joinToString(separator = "") {
            String.format("%02x", it xor seed)
        }
        return "$head-$session"
    }

    override fun getName(sessionId: String, sessionNameSet: Set<String>): Optional<String> {
        val split = sessionId.split(Regex("-"), 2)
        if (split.size != 2) {
            throw SessionException("无法解析 Session ID :$sessionId")
        }
        val uuid = UUID.fromString(split[1])
        val seed = (uuid.leastSignificantBits % Byte.MAX_VALUE).toByte()
        val head = split.first()
        if (head.length % 2 != 0) {
            throw SessionException("无法解析 Session ID :$sessionId")
        }
        val headStr =
            head.windowed(2, 2).map { (it.toInt(16).toByte() xor seed) }.toByteArray().toString(Charsets.ISO_8859_1)
        return Optional.ofNullable(sessionNameSet.firstOrNull { it.startsWith(headStr) })
    }
}
