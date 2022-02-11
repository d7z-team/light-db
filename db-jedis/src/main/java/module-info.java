import edgn.lightdb.api.LightDB;
import edgn.lightdb.jedis.JedisLightDB;

module edgn.lightdb.jedis {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    requires transitive edgn.objects.format.core;
    requires edgn.objects.format.ext.json;
    requires redis.clients.jedis;
    requires edgn.lightdb.api;
    exports edgn.lightdb.jedis;
    exports edgn.lightdb.jedis.options;
    provides LightDB with JedisLightDB;
}
