plugins {
    kotlin("jvm") version "2.2.21"
    `java-library`
    `maven-publish`
}

val gitVersion: String = try {
    providers.exec {
        commandLine("git", "describe", "--tags", "--abbrev=0")
    }.standardOutput.asText.get().trim()
} catch (_: Exception) {
    "0.0.0-dev"
}

group = "ru.raggies"
version = gitVersion

repositories {
    mavenCentral()
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    implementation("org.springframework.amqp:spring-rabbit:3.2.8")
    implementation("org.springframework.kafka:spring-kafka:3.3.15")
    implementation("org.springframework.boot:spring-boot-starter-web:3.5.4")

    testImplementation(kotlin("test"))
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])

            groupId = "com.github.37hulk37"
            artifactId = "raggies-common"
            version = gitVersion
        }
    }

    repositories {
        mavenLocal()
    }
}

tasks.test {
    useJUnitPlatform()
}