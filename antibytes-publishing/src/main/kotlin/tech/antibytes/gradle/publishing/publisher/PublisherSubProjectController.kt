/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache License, Version 2.0
 */

package tech.antibytes.gradle.publishing.publisher

import org.gradle.api.Project
import tech.antibytes.gradle.publishing.PublishingApiContract
import tech.antibytes.gradle.publishing.PublishingContract
import tech.antibytes.gradle.publishing.maven.MavenPublisher
import tech.antibytes.gradle.publishing.maven.MavenRepository

internal object PublisherSubProjectController : PublishingContract.PublisherController {
    private fun isApplicable(
        extension: PublishingContract.PublishingPluginExtension
    ): Boolean {
        return extension.repositoryConfiguration.isNotEmpty() &&
            extension.packageConfiguration is PublishingApiContract.PackageConfiguration
    }

    override fun configure(
        project: Project,
        version: String,
        extension: PublishingContract.PublishingPluginExtension
    ) {
        if (isApplicable(extension)) {
            MavenPublisher.configure(
                project,
                extension.packageConfiguration as PublishingApiContract.PackageConfiguration,
                version
            )

            extension.repositoryConfiguration.forEach { registry ->
                MavenRepository.configure(
                    project,
                    registry,
                    extension.dryRun
                )
            }
        }
    }
}
