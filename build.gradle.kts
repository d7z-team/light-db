group = rootProject.property("group")!!
version = rootProject.property("version")!!

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.open-edgn.cn/maven/")
        maven { url = project.uri("https://jitpack.io") }
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.10")
    }
}

allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        maven("https://repo.open-edgn.cn/maven/")
        maven { url = project.uri("https://jitpack.io") }
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
    for (childProject in childProjects.values) {
        delete(childProject.buildDir)
    }
}
