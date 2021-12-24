package edgn.lightdb.api.support.module

/**
 * 项目附加模块
 *
 * 注意：此接口仅用于二次开发和适配后端存储，
 * 请勿在生产环境手动实现此接口
 */
interface ValueModule {

    /**
     * 是否满足结果，如果结果不满足将导致数据不可见
     */
    val available: Boolean

    /**
     * 模块绑定钩子
     */
    fun bind()

    /**
     * 模块解绑钩子
     */
    fun unbind()
}
