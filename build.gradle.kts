import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val okhttp3_version: String by project
val mockk_version: String by project
val ktor_version: String by project
val logback_version: String by project
val koin_version: String by project

plugins {
    application
    kotlin("jvm") version "1.8.20"
    kotlin("plugin.serialization") version "1.7.10"
}

application {
    mainClass.set("com.github.ata.ApplicationKt")
}

kotlin {
    jvmToolchain {
        (this).languageVersion.set(JavaLanguageVersion.of(11))
    }
}

group = "com.github"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-locations:$ktor_version")
    implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")

    implementation("io.insert-koin:koin-ktor:$koin_version")

    implementation("com.squareup.okhttp3:okhttp:$okhttp3_version")

    implementation("ch.qos.logback:logback-classic:$logback_version")

    testImplementation(kotlin("test"))
    testImplementation("io.mockk:mockk:$mockk_version")
}

tasks {
    create("stage").dependsOn("installDist")
}

tasks.withType<Test> {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "${JavaVersion.VERSION_11}"
//        allWarningsAsErrors = true
    }
}
