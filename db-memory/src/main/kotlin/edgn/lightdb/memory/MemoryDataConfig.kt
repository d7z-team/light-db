package edgn.lightdb.memory

import edgn.lightdb.api.tables.DataConfig
import edgn.lightdb.api.tables.DataTable
import java.util.concurrent.TimeUnit

class MemoryDataConfig<TAB : DataTable<*>> : DataConfig<TAB> {
    private var currentTimeOut: Long
    private var currentTimeOutUnit: TimeUnit

    constructor() {
        currentTimeOut = Long.MAX_VALUE
        currentTimeOutUnit = TimeUnit.SECONDS
    }

    constructor(parent: MemoryDataConfig<*>) {
        currentTimeOut = parent.currentTimeOut
        currentTimeOutUnit = parent.currentTimeOutUnit
    }

    fun createHook(data: TAB) {
        data.expire(currentTimeOut, currentTimeOutUnit)
    }

    override fun defaultExpire(timeout: Long, unit: TimeUnit) {
        this.currentTimeOut = timeout
        this.currentTimeOutUnit = unit
    }
}
