import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val jedisVersion: String by rootProject
val objectFormat: String by rootProject
val slf4jVersion: String by rootProject
val springBootVersion: String by rootProject

plugins {
    kotlin("jvm")
    `maven-publish`
}
java.sourceCompatibility = JavaVersion.VERSION_11

dependencies {
    api(project(":db-api"))
    api(project(":db-memory"))
    api(project(":db-jedis")) {
        exclude("org.slf4j", "slf4j-api")
        exclude("org.json", "json") //  使用 com.vaadin.external.google.android-json
    }
    implementation(platform("com.github.d7z-team.object-format:bom:$objectFormat"))
    implementation("com.github.d7z-team.object-format", "format-core")
    implementation("com.github.d7z-team.object-format", "format-extra-jackson")
    implementation("com.github.d7z-team.object-format", "format-spring-boot-starter")
    api("org.springframework.boot", "spring-boot-autoconfigure", springBootVersion)
    annotationProcessor("org.springframework.boot", "spring-boot-configuration-processor", springBootVersion)
    testImplementation("org.springframework.boot", "spring-boot-starter-test", springBootVersion)
    testImplementation("org.springframework.boot", "spring-boot-starter-webflux", springBootVersion)
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
    repositories {
        mavenLocal()
    }
}
