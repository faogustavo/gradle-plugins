/*
 * Copyright (c) 2021 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache License, Version 2.0
 */

package tech.antibytes.gradle.plugin.dependency

object Dependency {
    val gradle = GradlePlugin

    object GradlePlugin {
        const val android = "com.android.tools.build:gradle:${Version.android}"
        const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Version.kotlin}"
        const val owasp = "org.owasp:dependency-check-gradle:${Version.gradle.owasp}"
        const val dependencyUpdate = "com.github.ben-manes:gradle-versions-plugin:${Version.gradle.dependencyUpdate}"
        const val jacoco = "org.jacoco:org.jacoco.core:${Version.gradle.jacoco}"
        const val publishing = "org.eclipse.jgit:org.eclipse.jgit:${Version.gradle.publishing}"
        const val versioning = "com.palantir.gradle.gitversion:gradle-git-version:${Version.gradle.versioning}"
    }

    val test = Test

    object Test {
        const val kotlinTest = "org.jetbrains.kotlin:kotlin-test-junit:${Version.kotlin}"
        const val junit = "org.junit:junit-bom:${Version.test.junit}"
        const val jupiter = "org.junit.jupiter:junit-jupiter"
        const val mockk = "io.mockk:mockk:${Version.test.mockk}"
        const val fixture = "com.appmattus.fixture:fixture:${Version.test.fixture}"
    }
}
