package tech.antibytes.gradle.dependency

import org.gradle.api.Plugin
import org.gradle.api.Project

class AntiBytesDependency : Plugin<Project> {
    override fun apply(target: Project) {
        val extension = target.extensions.create(
            "antiBytesDependency",
            AntiBytesDependencyExtension::class.java
        )

        if (!target.plugins.hasPlugin("com.github.ben-manes.versions")) {
            target.plugins.apply("com.github.ben-manes.versions")
        }

        DependencyUpdate.configure(target, extension)
    }
}
