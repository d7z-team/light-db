package edgn.lightdb.memory.impl.list

import edgn.lightdb.api.trees.DataTree
import edgn.lightdb.api.trees.list.LightListDB
import java.util.Optional
import kotlin.reflect.KClass

class MListDB : LightListDB {
    override fun <V : Any> get(key: String, wrap: KClass<V>): Optional<out DataTree<V>> {
        TODO("Not yet implemented")
    }

    override fun create(key: String, wrap: KClass<out Any>): Boolean {
        TODO("Not yet implemented")
    }

    override fun drop(key: String): Boolean {
        TODO("Not yet implemented")
    }

    override fun exists(key: String): Boolean {
        TODO("Not yet implemented")
    }
}
