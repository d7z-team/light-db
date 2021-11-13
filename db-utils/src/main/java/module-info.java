module edgn.lightdb.utils {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    requires transitive edgn.lightdb.api;
    opens edgn.lightdb.utils.format;
    exports edgn.lightdb.utils.format;
    opens edgn.lightdb.utils.config;
    exports edgn.lightdb.utils.config;
}
