import org.d7z.light.db.api.LightDB;

module org.d7z.light.db.api {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    exports org.d7z.light.db.api;
    exports org.d7z.light.db.api.error;
    exports org.d7z.light.db.api.struct;
    opens org.d7z.light.db.api;
    uses LightDB;
}
