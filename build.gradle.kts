import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val dockerUsername: String by project
val dockerPassword: String by project

plugins {
    application
    kotlin("jvm") version "1.7.0"
    id("com.google.cloud.tools.jib") version "3.2.1"
}

group = "com.github.plplmax"
version = "1.0"

jib {
    from {
        image = "azul/zulu-openjdk-alpine:17-jre"
        auth {
            username = dockerUsername
            password = dockerPassword
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("io.prometheus:simpleclient:0.16.0")
    implementation("io.prometheus:simpleclient_pushgateway:0.16.0")
    implementation("com.github.oshi:oshi-core-java11:6.2.2")
    implementation("org.slf4j:slf4j-simple:1.7.36")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("com.github.plplmax.monitoring.MainKt")
}