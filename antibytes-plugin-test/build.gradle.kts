import tech.antibytes.gradle.plugin.config.LibraryConfig

plugins {
    `kotlin-dsl`
    `java-library`
}

// To make it available as direct dependency
group = LibraryConfig.PublishConfig.groupId

object Version {
    const val kotlin = "1.5.31"
    const val junit = "5.8.1"
    const val mockk = "1.12.0"
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}")
    implementation("io.mockk:mockk:${Version.mockk}")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:${Version.kotlin}")
    testImplementation(platform("org.junit:junit-bom:${Version.junit}"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}
