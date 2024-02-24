plugins {
    id("java")
}

group = "org.milesjorgensen"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.assertj:assertj-core:3.25.3")
    implementation("io.appium:java-client:9.1.0")
}

tasks.test {
    useJUnit()
}