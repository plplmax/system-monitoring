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