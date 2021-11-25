/*
 * Copyright (c) 2021 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache License, Version 2.0
 */

package tech.antibytes.gradle.util

import org.gradle.api.Project

interface GradleUtilApiContract {
    enum class PlatformContext(val prefix: String) {
        ANDROID_APPLICATION("android"),
        ANDROID_APPLICATION_KMP("android"),
        ANDROID_LIBRARY("android"),
        ANDROID_LIBRARY_KMP("android"),
        JVM("jvm"),
        JVM_KMP("jvm")
    }

    interface PlatformContextResolver {
        fun getType(project: Project): Set<PlatformContext>
        fun isKmp(context: PlatformContext): Boolean
        fun isKmp(project: Project): Boolean
    }
}
