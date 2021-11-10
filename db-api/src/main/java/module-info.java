import edgn.lightdb.api.LightDB;

module edgn.lightdb.api {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    uses LightDB;
    opens edgn.lightdb.api;
    exports edgn.lightdb.api;
    opens edgn.lightdb.api.format;
    exports edgn.lightdb.api.format;
    opens edgn.lightdb.api.trees;
    exports edgn.lightdb.api.trees;
    opens edgn.lightdb.api.trees.list;
    exports edgn.lightdb.api.trees.list;
    opens edgn.lightdb.api.trees.set;
    exports edgn.lightdb.api.trees.set;
    opens edgn.lightdb.api.trees.map;
    exports edgn.lightdb.api.trees.map;
}
