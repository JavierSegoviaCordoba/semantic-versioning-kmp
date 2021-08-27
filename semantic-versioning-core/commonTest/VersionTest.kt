package com.javiersc.semanticVersioning

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class VersionTest {

    @Test
    fun `Same version`() {
        Version("1.0") shouldBe Version("1.0.0")
        Version("1.0.0") shouldBe Version("1.0.0")
        Version("1.1.0") shouldBe Version("1.1.0")
        Version("1.1.1") shouldBe Version("1.1.1")
        Version("1.1.1-alpha.1") shouldBe Version("1.1.1-alpha.1")
        Version("1.1.1-beta.1") shouldBe Version("1.1.1-beta.1")
        Version("0.1.0-beta.1") shouldBe Version("0.1.0-beta.1")
        Version("10.1.0-rc.3") shouldBe Version("10.1.0-rc.3")
    }

    @Test
    fun `Greater major`() {
        Version("2.0") shouldBeGreaterThan Version("1.0.0")
        Version("2.0.0") shouldBeGreaterThan Version("1.0")
        Version("2.0.0") shouldBeGreaterThan Version("1.0.1")
        Version("2.0.0") shouldBeGreaterThan Version("1.1.0")
        Version("2.0.0") shouldBeGreaterThan Version("1.1.1")
        Version("2.0.0") shouldBeGreaterThan Version("1.0.0-alpha.2")
        Version("2.0.0") shouldBeGreaterThan Version("1.1.0-alpha.2")
        Version("2.0.0") shouldBeGreaterThan Version("1.1.1-alpha.2")
        Version("2.0.0-alpha.2") shouldBeGreaterThan Version("1.1.1-alpha.1")
        Version("2.0.0-alpha.1") shouldBeGreaterThan Version("1.1.1-alpha.2")
        Version("2.0.0-alpha.1") shouldBeGreaterThan Version("1.1.1-beta.2")
    }

    @Test
    fun `Greater minor with same major`() {
        Version("1.1") shouldBeGreaterThan Version("1.0")
        Version("1.1") shouldBeGreaterThan Version("1.0.1")
        Version("1.1.0") shouldBeGreaterThan Version("1.0.1")
        Version("1.1.0") shouldBeGreaterThan Version("1.0.0-alpha.2")
        Version("1.1.0") shouldBeGreaterThan Version("1.1.0-alpha.2")
        Version("1.1-alpha.2") shouldBeGreaterThan Version("1.1-alpha.1")
        Version("1.1-alpha.2") shouldBeGreaterThan Version("1.1.0-alpha.1")
        Version("1.1.1-alpha.2") shouldBeGreaterThan Version("1.1.0-alpha.1")
        Version("1.1.1-alpha.1") shouldBeGreaterThan Version("1.1.0-alpha.2")
        Version("1.1.1-alpha.1") shouldBeGreaterThan Version("1.1.0-beta.2")
    }

    @Test
    fun `Greater patch with same major and minor`() {
        Version("1.1.1") shouldBeGreaterThan Version("1.1.0")
        Version("1.1.0") shouldBeGreaterThan Version("1.1.0-alpha.2")
        Version("1.1.1-alpha.2") shouldBeGreaterThan Version("1.1.0-alpha.1")
        Version("1.1.1-alpha.1") shouldBeGreaterThan Version("1.1.0-alpha.2")
        Version("1.1.1-alpha.1") shouldBeGreaterThan Version("1.1.0-beta.2")
    }

    @Test
    fun `Value is correct`() {
        Version("12.23.34", "alpha.5").value shouldBe "12.23.34-alpha.5"
        Version(1, 2, 3, "alpha", 1).value shouldBe "1.2.3-alpha.1"
    }

    @Test
    fun `Public properties and constructors with correct versions`() {
        with(Version("1.2")) {
            major shouldBe 1
            minor shouldBe 2
            patch shouldBe 0
            stage.shouldBeNull()
        }

        with(Version("1.2.3")) {
            major shouldBe 1
            minor shouldBe 2
            patch shouldBe 3
            stage.shouldBeNull()
        }

        with(Version("1.2.3", "")) {
            major shouldBe 1
            minor shouldBe 2
            patch shouldBe 3
            stage.shouldBeNull()
        }

        with(Version("1.2.3-alpha.3")) {
            major shouldBe 1
            minor shouldBe 2
            patch shouldBe 3
            stage shouldBe Version.Stage("alpha.3")
        }

        with(Version("1.2.3", "alpha.3")) {
            major shouldBe 1
            minor shouldBe 2
            patch shouldBe 3
            stage shouldBe Version.Stage("alpha.3")
        }
    }

    @Test
    fun `Public properties and constructors with incorrect versions`() {
        shouldThrow<VersionException> { Version("1a.2") }
        shouldThrow<VersionException> { Version("1a.2a") }
        shouldThrow<VersionException> { Version("1.2a") }
        shouldThrow<VersionException> { Version("1a.2.3") }
        shouldThrow<VersionException> { Version("1.2a.3a") }
        shouldThrow<VersionException> { Version("1.2.3a") }
        shouldThrow<VersionException> { Version("1a.2a.3") }
        shouldThrow<VersionException> { Version("1a.2.3a") }
        shouldThrow<VersionException> { Version("1a.2a.3a") }
        shouldThrow<VersionException> { Version("a1.2.3") }
        shouldThrow<VersionException> { Version("1.a2.3") }
        shouldThrow<VersionException> { Version("1.2.a3") }
        shouldThrow<VersionException> { Version("1.1.1.alpha.1") }
        shouldThrow<VersionException> { Version("1.1.1-alpha-1") }
        shouldThrow<VersionException> { Version("1-1.1.alpha-1") }
        shouldThrow<VersionException> { Version("1.1-1.alpha-1") }
        shouldThrow<VersionException> { Version("1-1-1.alpha-1") }
        shouldThrow<VersionException> { Version("1.2", "alpha.") }
        shouldThrow<VersionException> { Version("1.2", "alpha") }
        shouldThrow<VersionException> { Version("1.2", "11alpha") }
        shouldThrow<VersionException> { Version("1.2", "11alpha.2220s") }
        shouldThrow<VersionException> { Version("1.2", "alpha.2220s") }
        shouldThrow<VersionException> { Version("1.2.3", "alpha.2220s") }
        shouldThrow<VersionException> { Version("1.2.3", "alpha") }
        shouldThrow<VersionException> { Version("1.2.3", "alpha.") }
        shouldThrow<VersionException> { Version("1.2.3", "223alpha") }
        shouldThrow<VersionException> { Version("1.2.3", "223alpha.") }
        shouldThrow<VersionException> { Version(1, 2, 3, "-alpha", 1) }
        shouldThrow<VersionException> { Version(1, 2, 3, "alpha-", 1) }
        shouldThrow<VersionException> { Version(1, 2, 3, ".alpha", 1) }
        shouldThrow<VersionException> { Version(1, 2, 3, "alpha.", 1) }
        shouldThrow<VersionException> { Version(1, 2, 3, "alpha3232", 1) }
        shouldThrow<VersionException> { Version(1, 2, 3, "232alpha", 1) }
    }
}
