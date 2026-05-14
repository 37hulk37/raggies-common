plugins {
    kotlin("jvm") version "2.2.21"
    `java-library`
    `maven-publish`
}

group = "ru.raggies"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
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

            groupId = "ru.raggies"
            artifactId = "common"
            version = "1.0-SNAPSHOT"
        }
    }
}

tasks.test {
    useJUnitPlatform()
}