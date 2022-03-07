plugins {
    `java-platform`
    `maven-publish`
}

javaPlatform.allowDependencies()

dependencies {

    constraints {
        rootProject.subprojects
            .filter { it.name != project.name }
            .sortedBy { it.name }
            .forEach {
                api(it)
            }
    }
}

publishing {
    publications {
        create<MavenPublication>("bom") {
            groupId = rootProject.group.toString()
            artifactId = project.name
            version = rootProject.version.toString()
            from(components.getByName("javaPlatform"))
        }
    }
    repositories {
        mavenLocal()
    }
}
