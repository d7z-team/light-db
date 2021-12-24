import edgn.lightdb.api.LightDB;
import edgn.lightdb.jedis.JedisLightDB;

module edgn.lightdb.jedis {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    requires redis.clients.jedis;
    requires com.google.gson;
    requires edgn.lightdb.api;
    exports edgn.lightdb.jedis;
    provides LightDB with JedisLightDB;
}
