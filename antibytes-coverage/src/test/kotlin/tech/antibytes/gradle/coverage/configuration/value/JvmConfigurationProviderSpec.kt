/*
 * Copyright (c) 2022 Matthias Geisler (bitPogo) / All rights reserved.
 *
 * Use of this source code is governed by Apache License, Version 2.0
 */

package tech.antibytes.gradle.coverage.configuration.value

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import org.gradle.api.Project
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import tech.antibytes.gradle.coverage.api.JacocoReporterSettings
import tech.antibytes.gradle.coverage.configuration.ConfigurationContract
import tech.antibytes.gradle.coverage.source.SourceHelper
import tech.antibytes.gradle.util.GradleUtilApiContract.PlatformContext
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertSame
import kotlin.test.assertTrue

class JvmConfigurationProviderSpec {
    @BeforeEach
    fun setup() {
        mockkObject(SourceHelper)
    }

    @AfterEach
    fun tearDown() {
        unmockkObject(SourceHelper)
    }

    @Test
    fun `It fulfils DefaultPlatformConfigurationProvider`() {
        val provider: Any = JvmConfigurationProvider

        assertTrue(provider is ConfigurationContract.DefaultPlatformConfigurationProvider)
    }

    @Test
    fun `Given createDefaultCoverageConfiguration is called with a Project and PlatformContext, it returns a CoverageConfiguration, which contains default ReportSettings`() {
        every { SourceHelper.resolveSources(any(), any()) } returns mockk()

        // When
        val config = JvmConfigurationProvider.createDefaultCoverageConfiguration(
            mockk(),
            PlatformContext.JVM
        )

        // Then
        assertEquals(
            actual = config.reportSettings,
            expected = JacocoReporterSettings(),
        )
    }

    @Test
    fun `Given createDefaultCoverageConfiguration is called with a Project and PlatformContext, which does not indicate KMP, it returns a CoverageConfiguration, which contains the dependencies for TestTask`() {
        // Given
        val context = PlatformContext.JVM

        every { SourceHelper.resolveSources(any(), any()) } returns mockk()

        // When
        val config = JvmConfigurationProvider.createDefaultCoverageConfiguration(mockk(), context)

        // Then
        assertEquals(
            actual = config.testDependencies,
            expected = setOf("test"),
        )
    }

    @Test
    fun `Given createDefaultCoverageConfiguration is called with a Project and PlatformContext, which indicates KMP, it returns a CoverageConfiguration, which contains the dependencies for TestTask`() {
        // Given
        val context = PlatformContext.JVM_KMP

        every { SourceHelper.resolveSources(any(), any()) } returns mockk()

        // When
        val config = JvmConfigurationProvider.createDefaultCoverageConfiguration(mockk(), context)

        // Then
        assertEquals(
            actual = config.testDependencies,
            expected = setOf("jvmTest"),
        )
    }

    @Test
    fun `Given createDefaultCoverageConfiguration is called with a Project and PlatformContext, which indicates non KMP, it returns a CoverageConfiguration, which contains the default class pattern`() {
        // Given
        val context = PlatformContext.JVM

        every { SourceHelper.resolveSources(any(), any()) } returns mockk()

        // When
        val config = JvmConfigurationProvider.createDefaultCoverageConfiguration(mockk(), context)

        // Then
        assertEquals(
            actual = config.classPattern,
            expected = setOf(
                "build/classes/java/main/**/*.class",
                "build/classes/kotlin/main/**/*.class"
            ),
        )
    }

    @Test
    fun `Given createDefaultCoverageConfiguration is called with a Project and PlatformContext, which indicates KMP, it returns a CoverageConfiguration, which contains the default class pattern`() {
        // Given
        val context = PlatformContext.JVM_KMP

        every { SourceHelper.resolveSources(any(), any()) } returns mockk()

        // When
        val config = JvmConfigurationProvider.createDefaultCoverageConfiguration(mockk(), context)

        // Then
        assertEquals(
            actual = config.classPattern,
            expected = setOf(
                "build/classes/java/jvm/main/**/*.class",
                "build/classes/kotlin/jvm/main/**/*.class"
            ),
        )
    }

    @Test
    fun `Given createDefaultCoverageConfiguration is called with a Project and PlatformContext, which indicates non KMP, it returns a CoverageConfiguration, which contains the default class filter`() {
        // Given
        val context = PlatformContext.JVM

        every { SourceHelper.resolveSources(any(), any()) } returns mockk()

        // When
        val config = JvmConfigurationProvider.createDefaultCoverageConfiguration(mockk(), context)

        // Then
        assertEquals(
            actual = config.classFilter,
            expected = setOf(
                "build/classes/java/test/**/*.*",
                "build/classes/kotlin/test/**/*.*"
            ),
        )
    }

    @Test
    fun `Given createDefaultCoverageConfiguration is called with a Project and PlatformContext, which indicates KMP, it returns a CoverageConfiguration, which contains the default class filter`() {
        // Given
        val context = PlatformContext.JVM_KMP

        every { SourceHelper.resolveSources(any(), any()) } returns mockk()

        // When
        val config = JvmConfigurationProvider.createDefaultCoverageConfiguration(mockk(), context)

        // Then
        assertEquals(
            actual = config.classFilter,
            expected = setOf(
                "build/classes/java/jvm/test/**/*.*",
                "build/classes/kotlin/jvm/test/**/*.*"
            ),
        )
    }

    @Test
    fun `Given createDefaultCoverageConfiguration is called with a Project and PlatformContext, it returns a CoverageConfiguration, which contains the default class filter`() {
        // Given
        val context = PlatformContext.JVM_KMP

        every { SourceHelper.resolveSources(any(), any()) } returns mockk()

        // When
        val config = JvmConfigurationProvider.createDefaultCoverageConfiguration(mockk(), context)

        // Then
        assertEquals(
            actual = config.classFilter,
            expected = setOf(
                "build/classes/java/jvm/test/**/*.*",
                "build/classes/kotlin/jvm/test/**/*.*"
            ),
        )
    }

    @Test
    fun `Given createDefaultCoverageConfiguration is called with a Project and PlatformContext, it returns a CoverageConfiguration, which contains the sources`() {
        // Given
        val context = PlatformContext.JVM

        val project: Project = mockk()
        val sources: Set<File> = mockk()

        every { SourceHelper.resolveSources(project, context) } returns sources

        // When
        val config = JvmConfigurationProvider.createDefaultCoverageConfiguration(project, context)

        // Then
        assertSame(
            actual = config.sources,
            expected = sources
        )
    }

    @Test
    fun `Given createDefaultCoverageConfiguration is called with a Project and PlatformContext, it returns a CoverageConfiguration, which contains no additional classes`() {
        // Given
        val context = PlatformContext.JVM_KMP

        every { SourceHelper.resolveSources(any(), any()) } returns mockk()

        // When
        val config = JvmConfigurationProvider.createDefaultCoverageConfiguration(mockk(), context)

        // Then
        assertNull(config.additionalClasses)
    }

    @Test
    fun `Given createDefaultCoverageConfiguration is called with a Project and PlatformContext, it returns a CoverageConfiguration, which contains no additional sources`() {
        // Given
        val context = PlatformContext.JVM_KMP

        every { SourceHelper.resolveSources(any(), any()) } returns mockk()

        // When
        val config = JvmConfigurationProvider.createDefaultCoverageConfiguration(mockk(), context)

        // Then
        assertEquals(
            actual = config.additionalSources,
            expected = emptySet(),
        )
    }

    @Test
    fun `Given createDefaultCoverageConfiguration is called with a Project and PlatformContext, it returns a CoverageConfiguration, which contains no violation rules`() {
        // Given
        val context = PlatformContext.JVM_KMP

        every { SourceHelper.resolveSources(any(), any()) } returns mockk()

        // When
        val config = JvmConfigurationProvider.createDefaultCoverageConfiguration(mockk(), context)

        // Then
        assertEquals(
            actual = config.verificationRules,
            expected = emptySet(),
        )
    }
}
