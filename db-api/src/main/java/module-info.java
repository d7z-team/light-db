import edgn.lightdb.api.LightDB;

module edgn.lightdb.api {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    requires logger4k.core;
    opens edgn.lightdb.api;
    exports edgn.lightdb.api;
    uses LightDB;
}
