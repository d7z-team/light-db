import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
}
java.sourceCompatibility = JavaVersion.VERSION_11

dependencies {
    implementation(kotlin("reflect"))
    implementation(kotlin("stdlib"))
    implementation(project(":db-api"))
    implementation(project(":db-memory"))
    implementation(project(":db-jedis"))
    implementation("org.slf4j:slf4j-simple:2.0.0-alpha5")
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
