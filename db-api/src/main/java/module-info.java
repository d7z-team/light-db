import edgn.lightdb.api.LightDB;

module edgn.lightdb.api {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    uses LightDB;
    opens edgn.lightdb.api;
    exports edgn.lightdb.api;
    exports edgn.lightdb.api.structs;
    exports edgn.lightdb.api.structs.list;
    exports edgn.lightdb.api.structs.set;
    exports edgn.lightdb.api.structs.map;
    exports edgn.lightdb.api.utils;
}
