plugins {
    id("java")
}

group = "me.outspending"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("me.nullicorn:Nedit:2.2.0")
    implementation("org.slf4j:slf4j-api:2.0.12")

    implementation("it.unimi.dsi:fastutil:8.5.12")
    implementation("com.google.guava:guava:33.1.0-jre")

    implementation("com.google.code.gson:gson:2.10.1")

    compileOnly("org.jetbrains:annotations:24.1.0")

    compileOnly("org.projectlombok:lombok:1.18.32")
    annotationProcessor("org.projectlombok:lombok:1.18.32")
}