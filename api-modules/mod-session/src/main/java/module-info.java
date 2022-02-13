
module org.d7z.light.db.modules.session {
    requires kotlin.reflect;
    requires kotlin.stdlib;
    requires org.d7z.light.db.api;
    requires org.d7z.objects.format.all;
    exports org.d7z.light.db.modules.session.api;
    exports org.d7z.light.db.modules.session;
}
