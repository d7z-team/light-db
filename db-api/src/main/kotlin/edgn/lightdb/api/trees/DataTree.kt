package edgn.lightdb.api.trees

import java.util.concurrent.TimeUnit

interface DataTree<V : Any> {
    /**
     * 过期时间
     */
    val expire: Long

    /**
     * 设置过期时间
     */
    fun expire(timeout: Long, unit: TimeUnit)
}
