package edgn.lightdb.api.tables.list

import edgn.lightdb.api.tables.DataTable
import java.util.Optional

interface LightListTable<V : Any> : DataTable<V> {
    override fun get(): Optional<out LightListValue<V>>
}
