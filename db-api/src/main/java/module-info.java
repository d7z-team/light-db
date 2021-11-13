import edgn.lightdb.api.LightDB;

module edgn.lightdb.api {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    uses LightDB;
    opens edgn.lightdb.api;
    exports edgn.lightdb.api;
    opens edgn.lightdb.api.format;
    exports edgn.lightdb.api.format;
    opens edgn.lightdb.api.tables;
    exports edgn.lightdb.api.tables;
    opens edgn.lightdb.api.tables.list;
    exports edgn.lightdb.api.tables.list;
    opens edgn.lightdb.api.tables.set;
    exports edgn.lightdb.api.tables.set;
    opens edgn.lightdb.api.tables.map;
    exports edgn.lightdb.api.tables.map;
}
