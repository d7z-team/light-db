package org.d7z.light.db.modules.session.internal

import org.d7z.light.db.api.structs.map.LightMap
import org.d7z.light.db.api.utils.meta.ExpireMetaData
import org.d7z.light.db.api.utils.metaOrThrows
import org.d7z.light.db.modules.session.api.IExpireHook
import java.util.concurrent.TimeUnit

class SimpleExpireHook : IExpireHook {
    override fun ttl(lightMap: LightMap<String, String>): Long {
        return lightMap.metaOrThrows(ExpireMetaData::class).ttl
    }

    override fun ttl(lightMap: LightMap<String, String>, ttl: Long) {
        return lightMap.metaOrThrows(ExpireMetaData::class).expired(ttl, TimeUnit.SECONDS)
    }
}
