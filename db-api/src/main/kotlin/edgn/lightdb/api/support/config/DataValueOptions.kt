package edgn.lightdb.api.support.config

import java.util.Optional
import kotlin.reflect.KClass

interface DataValueOptions {
    /**
     * 实例的附加属性
     *
     * 如果属性不受支持则返回 Optional.empty() ,
     */
    fun <T : DataValueOption> option(option: KClass<T>): Optional<T>
}
