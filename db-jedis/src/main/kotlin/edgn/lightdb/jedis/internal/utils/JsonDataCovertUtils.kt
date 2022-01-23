package edgn.lightdb.jedis.internal.utils

import com.google.gson.Gson
import edgn.lightdb.jedis.options.DataCovert
import edgn.lightdb.jedis.options.DataCovert.CovertErrorException
import org.json.JSONObject
import java.lang.reflect.Proxy
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

/**
 * 基于 JSON 的对象转换工具
 */
class JsonDataCovertUtils : DataCovert {
    private val gson = Gson()
    override fun <T : Any> format(data: T, clazz: KClass<out T>): String {
        if (data is CharSequence) {
            return data.toString()
        }
        if (
            Proxy.isProxyClass(data::class.java) ||
            data::class.objectInstance != null ||
            clazz.qualifiedName == null
        ) {
            throw CovertErrorException("对象 $clazz 无法序列化.")
        }
        return gson.toJson(JsonBean(type = clazz.java.name, data = data))
    }

    override fun checkFormat(format: String, clazz: KClass<out Any>): Boolean {
        if (String::class.isSubclassOf(clazz)) {
            return true
        }
        return try {
            JSONObject(format).getString("type") == clazz.java.name
        } catch (e: Exception) {
            false
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> reduce(format: String, clazz: KClass<T>): T {
        if (String::class.isSubclassOf(clazz)) {
            return format as T
        }
        val jsonObject = JSONObject(format)
        val type = jsonObject.getString("type")
        return try {
            val implClazz = Class.forName(type)
            if (implClazz.kotlin.isSubclassOf(clazz)) {
                val data =
                    jsonObject.getJSONObject("data")
                gson.fromJson(data.toString(), implClazz) as T
            } else {
                throw ClassCastException("'$implClazz' != '$clazz'")
            }
        } catch (e: Exception) {
            throw CovertErrorException("对象 $clazz 无法反序列化.", e)
        }
    }

    data class JsonBean(
        val type: String,
        val data: Any
    )
}
