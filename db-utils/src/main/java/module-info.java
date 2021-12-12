module edgn.lightdb.utils {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    requires transitive edgn.lightdb.api;
    exports edgn.lightdb.utils.config;
    exports edgn.lightdb.utils.universal;
}
