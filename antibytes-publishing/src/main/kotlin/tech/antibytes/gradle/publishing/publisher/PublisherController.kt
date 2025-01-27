/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache License, Version 2.0
 */

package tech.antibytes.gradle.publishing.publisher

import org.gradle.api.Project
import org.gradle.api.Task
import tech.antibytes.gradle.publishing.PublishingApiContract
import tech.antibytes.gradle.publishing.PublishingContract
import tech.antibytes.gradle.publishing.Versioning
import tech.antibytes.gradle.util.isRoot

internal object PublisherController : PublishingContract.PublisherController {
    private fun addVersionTask(
        project: Project,
        configuration: PublishingApiContract.VersioningConfiguration
    ) {
        if (project.rootProject.tasks.findByName("versionInfo") !is Task) {
            project.rootProject.tasks.create("versionInfo") {
                group = "Versioning"
                description = "Displays the current version"

                doLast {
                    val info = Versioning.versionInfo(project, configuration)
                    println("version: ${info.name}")
                    println("last tag: ${info.details.lastTag}")
                    println("distance from last tag: ${info.details.commitDistance}")
                    println("clean: ${info.details.isCleanTag}")
                    println("hash: ${info.details.gitHashFull}")
                }
            }
        }
    }

    private fun setVersionToProject(project: Project, version: String) {
        project.version = version
    }

    override fun configure(
        project: Project,
        version: String,
        extension: PublishingContract.PublishingPluginExtension,
    ) {
        if (project.isRoot()) {
            project.evaluationDependsOnChildren()
        }

        project.afterEvaluate {
            val derivedVersion = Versioning.versionName(project, extension.versioning)

            addVersionTask(project, extension.versioning)
            setVersionToProject(project, derivedVersion)

            when {
                extension.excludeProjects.contains(project.name) -> { /* Do nothing */ }
                extension.standalone -> PublisherStandaloneController.configure(project, derivedVersion, extension)
                project.isRoot() -> PublisherRootProjectController.configure(project, derivedVersion, extension)
                else -> PublisherSubProjectController.configure(project, derivedVersion, extension)
            }
        }
    }
}
