/*
 * Copyright (c) 2021 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache License, Version 2.0
 */

package tech.antibytes.gradle.util

import com.appmattus.kotlinfixture.kotlinFixture
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.gradle.api.Project
import org.gradle.api.plugins.PluginContainer
import org.junit.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class ProjectExtensionSpec {
    private val fixture = kotlinFixture()

    @Test
    fun `Given isRoot is called with a RootProject it returns true`() {
        // Given
        val project: Project = mockk()

        every { project.rootProject } returns project

        // When
        val result = project.isRoot()

        // Then
        assertTrue(result)
    }

    @Test
    fun `Given isRoot is called with a SubProject it returns false`() {
        // Given
        val project: Project = mockk()

        every { project.rootProject } returns mockk()

        // When
        val result = project.isRoot()

        // Then
        assertFalse(result)
    }

    @Test
    fun `Given applyIfNotExists is called with a PluginsNames it applies them if they had not been applied`() {
        // Given
        val pluginNames: Set<String> = fixture()
        val project: Project = mockk()
        val plugins: PluginContainer = mockk()

        every { project.plugins } returns plugins
        every { plugins.hasPlugin(any<String>()) } returns false
        every { plugins.apply(any()) } returns mockk()

        // When
        project.applyIfNotExists(*pluginNames.toTypedArray())

        // Then
        pluginNames.forEach { pluginName ->
            verify(exactly = 1) { plugins.hasPlugin(pluginName) }
            verify(exactly = 1) { plugins.apply(pluginName) }
        }
    }

    @Test
    fun `Given applyIfNotExists is called with a PluginsNames it will not apply them if they had been already applied`() {
        // Given
        val pluginNames: Set<String> = fixture()
        val project: Project = mockk()
        val plugins: PluginContainer = mockk()

        every { project.plugins } returns plugins
        every { plugins.hasPlugin(any<String>()) } returns true
        every { plugins.apply(any()) } returns mockk()

        // When
        project.applyIfNotExists(*pluginNames.toTypedArray())

        // Then
        pluginNames.forEach { pluginName ->
            verify(exactly = 1) { plugins.hasPlugin(pluginName) }
            verify(exactly = 0) { plugins.apply(pluginName) }
        }
    }
}
