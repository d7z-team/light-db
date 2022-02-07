package edgn.lightdb.modules.cache.api

import edgn.lightdb.modules.cache.internal.SimpleMultipleKey

/**
 * 多入参包装，
 */
interface IMultipleKey {
    /**
     * 由多入参计算单出参结果
     */
    val singleKey: String

    companion object {

        /**
         * 快速创建多入参
         * @param key Array<out Any> 目标参数
         */
        @Suppress("unused")
        @JvmStatic
        fun keysOf(vararg key: Any): IMultipleKey {
            return SimpleMultipleKey(*key)
        }
    }

    override fun toString(): String
}
