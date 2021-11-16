package edgn.lightdb.api.tables.list

import edgn.lightdb.api.tables.DataTable
import java.util.Optional

interface LightListTable<V : Any> : DataTable<V> {
    override fun items(): Optional<out LightListValue<V>>
}
