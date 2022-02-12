import org.d7z.light.db.api.LightDB;
import org.d7z.light.db.jedis.JedisLightDB;

module org.d7z.light.db.jedis {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    requires transitive org.d7z.objects.format.core;
    requires org.d7z.objects.format.ext.json;
    requires org.d7z.light.db.api;
    requires redis.clients.jedis;
    exports org.d7z.light.db.jedis;
    exports org.d7z.light.db.jedis.options;
    provides LightDB with JedisLightDB;
}
