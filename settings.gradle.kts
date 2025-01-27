/*
 * Copyright (c) 2021 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache License, Version 2.0
 */

pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
    }
}

includeBuild("localPlugin/dependency")

include(
    ":antibytes-coverage",
    ":antibytes-gradle-test-utils",
    ":antibytes-dependency",
    ":antibytes-publishing",
    ":antibytes-configuration",
    ":antibytes-gradle-utils",
    ":antibytes-grammar-tools",
    ":antibytes-runtime-configuration"
)

buildCache {
    local {
        isEnabled = false
        directory = File(rootDir, "build-cache")
        removeUnusedEntriesAfterDays = 30
    }
}

rootProject.name = "gradle-plugins"
