import org.d7z.light.db.api.LightDB;
import org.d7z.light.db.memory.MemoryLightDB;

module org.d7z.light.db.memory {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    requires org.d7z.light.db.api;
    opens org.d7z.light.db.memory;
    exports org.d7z.light.db.memory;
    provides LightDB with MemoryLightDB;
}
