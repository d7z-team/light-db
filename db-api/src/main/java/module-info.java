import org.d7z.light.db.api.LightDB;

module org.d7z.light.db.api {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    uses LightDB;
    opens org.d7z.light.db.api;
    exports org.d7z.light.db.api;
    exports org.d7z.light.db.api.structs;
    exports org.d7z.light.db.api.structs.list;
    exports org.d7z.light.db.api.structs.set;
    exports org.d7z.light.db.api.structs.map;
    exports org.d7z.light.db.api.utils;
    exports org.d7z.light.db.api.utils.meta;
}
