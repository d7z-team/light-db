import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    `maven-publish`
}
java.sourceCompatibility = JavaVersion.VERSION_11

val compileKotlin: KotlinCompile by tasks
val compileJava: JavaCompile by tasks
compileJava.destinationDirectory.set(compileKotlin.destinationDirectory.get())

java {
    modularity.inferModulePath.set(true)
}

dependencies {
    api("redis.clients:jedis:4.2.0")
    api("com.github.d7z-team.object-format:format-all:0.3.0")
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    api(project(":db-api"))
    testImplementation("org.slf4j:slf4j-simple:1.7.36")
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.junit.platform:junit-platform-launcher:1.8.2")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
    if (System.getenv("JEDIS_PASSWORD") == null) {
        this.environment["JEDIS_PASSWORD"] = "redis-password"
    }
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions.jvmTarget = "11"
}
val sourcesJar by tasks.registering(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = rootProject.group.toString()
            artifactId = project.name
            version = rootProject.version.toString()
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
    repositories {
        mavenLocal()
    }
}
