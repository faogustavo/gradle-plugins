/*
 * Copyright (c) 2021 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache License, Version 2.0
 */

import tech.antibytes.gradle.plugin.config.LibraryConfig
import tech.antibytes.gradle.plugin.dependency.Version
import tech.antibytes.gradle.plugin.dependency.Dependency


plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
    jacoco

    id("tech.antibytes.gradle.plugin.script.maven-package")
}

jacoco {
    version = Version.gradle.jacoco
}

// To make it available as direct dependency
group = LibraryConfig.PublishConfig.groupId

dependencies {
    implementation(Dependency.gradle.kotlin)
    implementation(Dependency.gradle.android)
    implementation(project(":antibytes-gradle-utils"))
    implementation(Dependency.square.kotlinPoet.core)

    testImplementation(Dependency.test.kotlinTest)
    testImplementation(platform(Dependency.test.junit))
    testImplementation(Dependency.test.jupiter)
    testImplementation(Dependency.test.mockk)
    testImplementation(Dependency.test.fixture)
    testImplementation(project(":antibytes-gradle-test-utils"))
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

gradlePlugin {
    plugins.register("${LibraryConfig.group}.gradle.runtime-configuration") {
        group = LibraryConfig.group
        id = "${LibraryConfig.group}.gradle.runtime-configuration"
        displayName = "${id}.gradle.plugin"
        implementationClass = "tech.antibytes.gradle.configuration.runtime.AntiBytesRuntimeConfiguration"
        description = "Adds a Runtime Configuration Object for Antibytes projects"
        version = "0.1.0"
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.jacocoTestReport {
    dependsOn(tasks.named("test"))

    reports {
        html.required.set(true)
        xml.required.set(true)
        csv.required.set(true)

        html.outputLocation.set(
            layout.buildDirectory.dir("reports/jacoco/test/${project.name}").get().asFile
        )
        csv.outputLocation.set(
            layout.buildDirectory.file("reports/jacoco/test/${project.name}.csv").get().asFile
        )
        xml.outputLocation.set(
            layout.buildDirectory.file("reports/jacoco/test/${project.name}.xml").get().asFile
        )
    }
}

tasks.jacocoTestCoverageVerification {
    dependsOn(tasks.named("jacocoTestReport"))
    violationRules {
        rule {
            enabled = false
            limit {
                counter = "BRANCH"
                value = "COVEREDRATIO"
                minimum = BigDecimal(0.99)
            }
        }
        rule {
            enabled = false
            limit {
                counter = "INSTRUCTION"
                value = "COVEREDRATIO"
                minimum = BigDecimal( 0.89)
            }
        }
    }
}

tasks.check {
    dependsOn("jacocoTestCoverageVerification")
}
