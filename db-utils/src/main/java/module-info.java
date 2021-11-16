module edgn.lightdb.utils {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    requires transitive edgn.lightdb.api;
    exports edgn.lightdb.utils;
    opens edgn.lightdb.utils;
}
