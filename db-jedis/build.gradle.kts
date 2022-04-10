import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val jedisVersion: String by rootProject
val objectFormat: String by rootProject
val slf4jVersion: String by rootProject

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
    api("redis.clients:jedis:$jedisVersion") {
        exclude("org.slf4j", "slf4j-api")
    }
    implementation("org.slf4j:slf4j-api:$slf4jVersion")
    api("com.github.d7z-team.object-format:format-core:$objectFormat")
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    api(project(":db-api"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.8.2")
    testImplementation("org.junit.platform:junit-platform-launcher:1.8.2")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
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
    includeRepositories(project)
}
