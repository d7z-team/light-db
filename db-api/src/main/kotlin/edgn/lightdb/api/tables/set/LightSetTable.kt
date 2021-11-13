package edgn.lightdb.api.tables.set

import edgn.lightdb.api.tables.DataTable
import java.util.Optional

interface LightSetTable<V : Any> : DataTable<V> {
    override fun get(): Optional<LightSetValue<V>>
}
