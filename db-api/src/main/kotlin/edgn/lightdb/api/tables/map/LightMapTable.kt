package edgn.lightdb.api.tables.map

import edgn.lightdb.api.tables.DataTable
import java.util.Optional

interface LightMapTable<V : Any> : DataTable<V> {
    override fun get(): Optional<out LightMapValue<V>>
}
