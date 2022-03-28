package org.d7z.light.db.test

import org.d7z.light.db.api.LightDB
import org.d7z.logger4k.core.utils.getLogger

class Test(private val lightDB: LightDB) {
    private val logger = getLogger()

    init {
        logger.info("发现 LightDB 实现 {}.", lightDB.name)
    }

    fun start() {
    }
}
