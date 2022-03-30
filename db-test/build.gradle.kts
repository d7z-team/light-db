import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}
java.sourceCompatibility = JavaVersion.VERSION_11

val slf4jVersion: String by rootProject

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    implementation(project(":db-api"))
    implementation(project(":db-memory"))
    implementation(project(":db-jedis"))
    implementation("com.github.d7z-team.logger4k:logger-core:0.2.1")
    implementation("com.github.d7z-team.logger4k:logger-forward:0.2.1")
    implementation("org.slf4j:slf4j-simple:$slf4jVersion")
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
