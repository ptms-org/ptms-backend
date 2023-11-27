plugins {
    java
    id("org.springframework.boot") version "3.1.5"
    id("io.spring.dependency-management") version "1.1.3"
    id("idea")
}

group = "com"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

sourceSets {
    create("integrationTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

val integrationTestImplementation by configurations.getting {
    extendsFrom(configurations.implementation.get())
}

configurations["integrationTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    runtimeOnly("org.postgresql:postgresql")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.testcontainers:junit-jupiter")

    integrationTestImplementation("org.springframework.boot:spring-boot-starter-test")
    integrationTestImplementation("org.springframework.boot:spring-boot-testcontainers")
    integrationTestImplementation("org.testcontainers:junit-jupiter")
    integrationTestImplementation("org.testcontainers:postgresql")

}

tasks.withType<Test> {
    useJUnitPlatform()
}

val integrationTest = task<Test>("integrationTest") {
    description = "Runs integration tests."
    group = "verification"

    testClassesDirs = sourceSets["integrationTest"].output.classesDirs
    classpath = sourceSets["integrationTest"].runtimeClasspath
    shouldRunAfter("test")
}
tasks.getByName<Test>("integrationTest") {
    useJUnitPlatform()
}

tasks.check {
    dependsOn(integrationTest)
}
