package edgn.lightdb.api.trees.map

import edgn.lightdb.api.trees.DataTree

interface LightMap<V : Any> : MutableMap<String, V>, DataTree<V>
