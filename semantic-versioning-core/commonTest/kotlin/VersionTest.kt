package com.javiersc.semanticVersioning

import com.javiersc.semanticVersioning.Version.Increase.*
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import kotlin.test.Test

class VersionTest {

    @Test
    fun same_version() {
        Version("1.0.0") shouldBe Version("1.0.0")
        Version("1.1.0") shouldBe Version("1.1.0")
        Version("1.1.1") shouldBe Version("1.1.1")
        Version("1.1.0-alpha.1") shouldBe Version("1.1.0-alpha.1")
        Version("1.1.1-alpha.1") shouldBe Version("1.1.1-alpha.1")
        Version("1.1.1-beta.1") shouldBe Version("1.1.1-beta.1")
        Version("0.1.0-beta.1") shouldBe Version("0.1.0-beta.1")
        Version("10.1.0-rc.3") shouldBe Version("10.1.0-rc.3")
        Version("1.0.0-SNAPSHOT") shouldBe Version("1.0.0-SNAPSHOT")
        Version("2.1.0-SNAPSHOT") shouldBe Version("2.1.0-SNAPSHOT")
        Version("3.4.2-SNAPSHOT") shouldBe Version("3.4.2-SNAPSHOT")
    }

    @Test
    fun greater_major() {
        Version("2.0.0") shouldBeGreaterThan Version("1.0.0")
        Version("2.0.0") shouldBeGreaterThan Version("1.0.1")
        Version("2.0.0") shouldBeGreaterThan Version("1.1.0")
        Version("2.0.0") shouldBeGreaterThan Version("1.1.1")
        Version("2.0.0") shouldBeGreaterThan Version("1.0.0-alpha.2")
        Version("2.0.0") shouldBeGreaterThan Version("1.1.0-alpha.2")
        Version("2.0.0") shouldBeGreaterThan Version("1.1.1-alpha.2")
        Version("2.0.0-alpha.2") shouldBeGreaterThan Version("1.1.1-alpha.1")
        Version("2.0.0-alpha.1") shouldBeGreaterThan Version("1.1.1-alpha.2")
        Version("2.0.0-alpha.1") shouldBeGreaterThan Version("1.1.1-beta.2")
        Version("2.0.0") shouldBeGreaterThan Version("1.0.0-SNAPSHOT")
        Version("2.0.0-SNAPSHOT") shouldBeGreaterThan Version("1.0.0")
        Version("2.0.0-SNAPSHOT") shouldBeGreaterThan Version("1.0.0-alpha.1")
        Version("2.0.0-alpha.1") shouldBeGreaterThan Version("1.0.0-SNAPSHOT")
    }

    @Test
    fun greater_minor_with_same_major() {
        Version("1.1.0") shouldBeGreaterThan Version("1.0.1")
        Version("1.1.0") shouldBeGreaterThan Version("1.0.0-alpha.2")
        Version("1.1.0") shouldBeGreaterThan Version("1.1.0-alpha.2")
        Version("1.1.0-alpha.2") shouldBeGreaterThan Version("1.1.0-alpha.1")
        Version("1.1.1-alpha.2") shouldBeGreaterThan Version("1.1.0-alpha.1")
        Version("1.1.1-alpha.1") shouldBeGreaterThan Version("1.1.0-alpha.2")
        Version("1.1.1-alpha.1") shouldBeGreaterThan Version("1.1.0-beta.2")
        Version("1.1.1-SNAPSHOT") shouldBeGreaterThan Version("1.1.0-beta.2")
    }

    @Test
    fun greater_patch_with_same_major_and_minor() {
        Version("1.1.1") shouldBeGreaterThan Version("1.1.0")
        Version("1.1.0") shouldBeGreaterThan Version("1.1.0-alpha.2")
        Version("1.1.1-alpha.2") shouldBeGreaterThan Version("1.1.0-alpha.1")
        Version("1.1.1-alpha.1") shouldBeGreaterThan Version("1.1.0-alpha.2")
        Version("1.1.1-alpha.1") shouldBeGreaterThan Version("1.1.0-beta.2")
        Version("1.1.1-SNAPSHOT") shouldBeGreaterThan Version("1.1.0-beta.2")
    }

    @Test
    fun greater_stage_with_same_major_minor_and_patch() {
        Version("1.0.0-beta.1") shouldBeGreaterThan Version("1.0.0-alpha.1")
        Version("1.0.0-rc.1") shouldBeGreaterThan Version("1.0.0-alpha.1")
        Version("1.0.0-alpha.1") shouldBeGreaterThan Version("1.0.0-SNAPSHOT")
        Version("1.0.0-rc.1") shouldBeGreaterThan Version("1.0.0-SNAPSHOT")
        Version("1.0.0") shouldBeGreaterThan Version("1.0.0-SNAPSHOT")
    }

    @Test
    fun value_is_correct() {
        Version("12.23.34-SNAPSHOT").value shouldBe "12.23.34-SNAPSHOT"
        Version("12.23.34", "alpha.5").value shouldBe "12.23.34-alpha.5"
        Version("12.23.34", "SNAPSHOT").value shouldBe "12.23.34-SNAPSHOT"
        Version(1, 2, 3, "alpha", 1).value shouldBe "1.2.3-alpha.1"
        Version(1, 2, 3, "SNAPSHOT", null).value shouldBe "1.2.3-SNAPSHOT"
    }

    @Test
    fun public_properties_and_constructors_with_correct_versions() {
        with(Version("1.2.0")) {
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
            stage?.name shouldBe "alpha"
            stage?.num shouldBe 3
        }

        with(Version("1.2.3", "alpha.3")) {
            major shouldBe 1
            minor shouldBe 2
            patch shouldBe 3
            stage?.name shouldBe "alpha"
            stage?.num shouldBe 3
        }

        with(Version("1.2.3", "SNAPSHOT")) {
            major shouldBe 1
            minor shouldBe 2
            patch shouldBe 3
            stage?.name shouldBe "SNAPSHOT"
            stage?.num.shouldBeNull()
        }
    }

    @Test
    fun public_properties_and_constructors_with_incorrect_versions() {
        shouldThrow<SemanticVersionException> { Version("1.0") }
        shouldThrow<SemanticVersionException> { Version("3.53") }
        shouldThrow<SemanticVersionException> { Version("222.22") }
        shouldThrow<SemanticVersionException> { Version("4223.4343") }
        shouldThrow<SemanticVersionException> { Version("1.0-snapshot.1") }
        shouldThrow<SemanticVersionException> { Version("1.0-SNAPSHOT.1") }
        shouldThrow<SemanticVersionException> { Version("1.0.0-SNAPSHOT.1") }
        shouldThrow<SemanticVersionException> { Version("1.0.0-SNAPSHOT.1") }
        shouldThrow<SemanticVersionException> { Version("1.0", "SNAPSHOT.1") }
        shouldThrow<SemanticVersionException> { Version("1.0.0", "SNAPSHOT.1") }
        shouldThrow<SemanticVersionException> { Version(1, 2, 3, "SNAPSHOT", 1) }
        shouldThrow<SemanticVersionException> { Version("1a.2") }
        shouldThrow<SemanticVersionException> { Version("1a.2a") }
        shouldThrow<SemanticVersionException> { Version("1.2a") }
        shouldThrow<SemanticVersionException> { Version("1a.2.3") }
        shouldThrow<SemanticVersionException> { Version("1.2a.3a") }
        shouldThrow<SemanticVersionException> { Version("1.2.3a") }
        shouldThrow<SemanticVersionException> { Version("1a.2a.3") }
        shouldThrow<SemanticVersionException> { Version("1a.2.3a") }
        shouldThrow<SemanticVersionException> { Version("1a.2a.3a") }
        shouldThrow<SemanticVersionException> { Version("a1.2.3") }
        shouldThrow<SemanticVersionException> { Version("1.a2.3") }
        shouldThrow<SemanticVersionException> { Version("1.2.a3") }
        shouldThrow<SemanticVersionException> { Version("1.1.1.alpha.1") }
        shouldThrow<SemanticVersionException> { Version("1.1.1-alpha-1") }
        shouldThrow<SemanticVersionException> { Version("1-1.1.alpha-1") }
        shouldThrow<SemanticVersionException> { Version("1.1-1.alpha-1") }
        shouldThrow<SemanticVersionException> { Version("1-1-1.alpha-1") }
        shouldThrow<SemanticVersionException> { Version("1.2", "alpha.") }
        shouldThrow<SemanticVersionException> { Version("1.2", "alpha") }
        shouldThrow<SemanticVersionException> { Version("1.2", "11alpha") }
        shouldThrow<SemanticVersionException> { Version("1.2", "11alpha.2220s") }
        shouldThrow<SemanticVersionException> { Version("1.2", "alpha.2220s") }
        shouldThrow<SemanticVersionException> { Version("1.2.3", "alpha.2220s") }
        shouldThrow<SemanticVersionException> { Version("1.2.3", "alpha") }
        shouldThrow<SemanticVersionException> { Version("1.2.3", "alpha.") }
        shouldThrow<SemanticVersionException> { Version("1.2.3", "223alpha") }
        shouldThrow<SemanticVersionException> { Version("1.2.3", "223alpha.") }
        shouldThrow<SemanticVersionException> { Version(1, 2, 3, "-alpha", 1) }
        shouldThrow<SemanticVersionException> { Version(1, 2, 3, "alpha-", 1) }
        shouldThrow<SemanticVersionException> { Version(1, 2, 3, ".alpha", 1) }
        shouldThrow<SemanticVersionException> { Version(1, 2, 3, "alpha.", 1) }
        shouldThrow<SemanticVersionException> { Version(1, 2, 3, "alpha3232", 1) }
        shouldThrow<SemanticVersionException> { Version(1, 2, 3, "232alpha", 1) }
        shouldThrow<SemanticVersionException> { Version("1.0").copy(stageName = "alpha") }
    }

    @Test
    fun increase_stage() {
        Version("1.0.0").inc(stageName = "alpha") shouldBe Version("1.0.1-alpha.1")
        Version("1.0.0-alpha.1").inc(stageName = "alpha") shouldBe Version("1.0.0-alpha.2")
        Version("1.0.0-alpha.1").inc() shouldBe Version("1.0.0")
        Version("1.0.0-alpha.1").inc(stageName = "beta") shouldBe Version("1.0.0-beta.1")
        Version("1.1.0-beta.1").inc(stageName = "rc") shouldBe Version("1.1.0-rc.1")
        Version("1.1.0").inc(stageName = "rc") shouldBe Version("1.1.1-rc.1")
        Version("1.1.0-SNAPSHOT").inc(stageName = "rc") shouldBe Version("1.1.0-rc.1")
        Version("1.1.0-SNAPSHOT").inc() shouldBe Version("1.1.0")
        shouldThrow<SemanticVersionException> { Version("1.1.0-beta.1").inc(stageName = "alpha") }
        shouldThrow<SemanticVersionException> { Version("1.0.0").inc(stageName = "snapshot") }
        shouldThrow<SemanticVersionException> { Version("1.0.0").inc(stageName = "SNAPSHOT") }
    }

    @Test
    fun increase_stage_and_patch() {
        Version("1.0.0").inc(Patch, "alpha") shouldBe Version("1.0.1-alpha.1")
        Version("1.0.0-alpha.1").inc(Patch, "alpha") shouldBe Version("1.0.1-alpha.2")
        Version("1.0.0-alpha.1").inc(Patch, "") shouldBe Version("1.0.1")
        Version("1.0.0-alpha.1").inc(Patch, "beta") shouldBe Version("1.0.1-beta.1")
        Version("1.1.0-beta.1").inc(Patch, "rc") shouldBe Version("1.1.1-rc.1")
        Version("1.1.0").inc(Patch, "rc") shouldBe Version("1.1.1-rc.1")
        Version("1.1.0-SNAPSHOT").inc(Patch, "rc") shouldBe Version("1.1.1-rc.1")
        Version("1.1.0-SNAPSHOT").inc(Patch) shouldBe Version("1.1.1")
        Version("1.1.0-beta.1").inc(Patch, "alpha") shouldBe Version("1.1.1-alpha.1")
        shouldThrow<SemanticVersionException> { Version("1.0.0").inc(Patch, "snapshot") }
        shouldThrow<SemanticVersionException> { Version("1.0.0").inc(Patch, "SNAPSHOT") }
    }

    @Test
    fun increase_stage_and_minor() {
        Version("1.0.0").inc(Minor, "alpha") shouldBe Version("1.1.0-alpha.1")
        Version("1.0.0-alpha.1").inc(Minor, "alpha") shouldBe Version("1.1.0-alpha.2")
        Version("1.0.0-alpha.1").inc(Minor, "") shouldBe Version("1.1.0")
        Version("1.0.0-alpha.1").inc(Minor, "beta") shouldBe Version("1.1.0-beta.1")
        Version("1.1.0-beta.1").inc(Minor, "rc") shouldBe Version("1.2.0-rc.1")
        Version("1.1.0").inc(Minor, "rc") shouldBe Version("1.2.0-rc.1")
        Version("1.1.0-SNAPSHOT").inc(Minor, "rc") shouldBe Version("1.2.0-rc.1")
        Version("1.1.0-SNAPSHOT").inc(Minor) shouldBe Version("1.2.0")
        Version("1.1.0-beta.1").inc(Minor, "alpha") shouldBe Version("1.2.0-alpha.1")
        shouldThrow<SemanticVersionException> { Version("1.0.0").inc(Minor, "snapshot") }
        shouldThrow<SemanticVersionException> { Version("1.0.0").inc(Minor, "SNAPSHOT") }
    }

    @Test
    fun increase_stage_and_major() {
        Version("1.0.0").inc(Major, "alpha") shouldBe Version("2.0.0-alpha.1")
        Version("1.0.0-alpha.1").inc(Major, "alpha") shouldBe Version("2.0.0-alpha.2")
        Version("1.0.0-alpha.1").inc(Major, "") shouldBe Version("2.0.0")
        Version("1.0.0-alpha.1").inc(Major, "beta") shouldBe Version("2.0.0-beta.1")
        Version("1.1.0-beta.1").inc(Major, "rc") shouldBe Version("2.0.0-rc.1")
        Version("1.1.0").inc(Major, "rc") shouldBe Version("2.0.0-rc.1")
        Version("1.1.0-SNAPSHOT").inc(Major, "rc") shouldBe Version("2.0.0-rc.1")
        Version("1.1.0-SNAPSHOT").inc(Major) shouldBe Version("2.0.0")
        Version("1.1.0-beta.1").inc(Major, "alpha") shouldBe Version("2.0.0-alpha.1")
        shouldThrow<SemanticVersionException> { Version("1.0.0").inc(Major, "snapshot") }
        shouldThrow<SemanticVersionException> { Version("1.0.0").inc(Major, "SNAPSHOT") }
    }

    @Test
    fun increase_patch() {
        Version("1.0.0").inc(Patch) shouldBe Version("1.0.1")
        Version("1.1.0").inc(Patch) shouldBe Version("1.1.1")
        Version("1.1.1").inc(Patch) shouldBe Version("1.1.2")
        Version("1.1.1-alpha.1").inc(Patch) shouldBe Version("1.1.2")
        Version("1.1.1-beta.1").inc(Patch) shouldBe Version("1.1.2")
        Version("0.1.0-beta.1").inc(Patch) shouldBe Version("0.1.1")
        Version("10.1.0-rc.3").inc(Patch) shouldBe Version("10.1.1")
        Version("1.0.0-SNAPSHOT").inc(Patch) shouldBe Version("1.0.1")
    }

    @Test
    fun increase_minor() {
        Version("1.0.0").inc(Minor) shouldBe Version("1.1.0")
        Version("1.1.0").inc(Minor) shouldBe Version("1.2.0")
        Version("1.1.1").inc(Minor) shouldBe Version("1.2.0")
        Version("1.1.1-alpha.1").inc(Minor) shouldBe Version("1.2.0")
        Version("1.1.1-beta.1").inc(Minor) shouldBe Version("1.2.0")
        Version("0.1.0-beta.1").inc(Minor) shouldBe Version("0.2.0")
        Version("10.1.0-rc.3").inc(Minor) shouldBe Version("10.2.0")
        Version("1.0.0-SNAPSHOT").inc(Minor) shouldBe Version("1.1.0")
    }

    @Test
    fun increase_major() {
        Version("1.0.0").inc(Major) shouldBe Version("2.0.0")
        Version("1.1.0").inc(Major) shouldBe Version("2.0.0")
        Version("1.1.1").inc(Major) shouldBe Version("2.0.0")
        Version("1.1.1-alpha.1").inc(Major) shouldBe Version("2.0.0")
        Version("1.1.1-beta.1").inc(Major) shouldBe Version("2.0.0")
        Version("0.1.0-beta.1").inc(Major) shouldBe Version("1.0.0")
        Version("10.1.0-rc.3").inc(Major) shouldBe Version("11.0.0")
        Version("1.0.0-SNAPSHOT").inc(Major) shouldBe Version("2.0.0")
    }

    @Test
    fun copy_all_versions() {
        Version("1.0.0").copy(major = 3) shouldBe Version("3.0.0")
        Version("1.0.0").copy(minor = 3) shouldBe Version("1.3.0")
        Version("1.0.0").copy(patch = 3) shouldBe Version("1.0.3")
        Version("1.0.0").copy(stageName = "alpha", stageNum = 3) shouldBe Version("1.0.0-alpha.3")
        Version("1.0.0").copy(stageName = "SNAPSHOT") shouldBe Version("1.0.0-SNAPSHOT")
        Version("1.0.1").copy(major = 3) shouldBe Version("3.0.1")
        Version("1.0.1").copy(minor = 3) shouldBe Version("1.3.1")
        Version("1.0.1").copy(patch = 3) shouldBe Version("1.0.3")
        Version("1.0.1").copy(stageName = "alpha", stageNum = 3) shouldBe Version("1.0.1-alpha.3")
        Version("1.0.1").copy(stageName = "SNAPSHOT") shouldBe Version("1.0.1-SNAPSHOT")
        Version("1.1.0").copy(major = 3) shouldBe Version("3.1.0")
        Version("1.1.0").copy(minor = 3) shouldBe Version("1.3.0")
        Version("1.1.0").copy(patch = 3) shouldBe Version("1.1.3")
        Version("1.1.0").copy(stageName = "alpha", stageNum = 3) shouldBe Version("1.1.0-alpha.3")
        Version("1.1.0").copy(stageName = "SNAPSHOT") shouldBe Version("1.1.0-SNAPSHOT")
        Version("1.1.0-alpha.1").copy(major = 3) shouldBe Version("3.1.0-alpha.1")
        Version("1.1.0-alpha.1").copy(minor = 3) shouldBe Version("1.3.0-alpha.1")
        Version("1.1.0-alpha.1").copy(patch = 3) shouldBe Version("1.1.3-alpha.1")
        Version("1.1.0-alpha.1").copy(stageName = "beta", stageNum = 3) shouldBe
            Version("1.1.0-beta.3")
        Version("1.1.0-alpha.1").copy(stageName = "SNAPSHOT") shouldBe Version("1.1.0-SNAPSHOT")
    }

    @Test
    fun next_snapshot() {
        Version("1.0.0").nextSnapshotPatch() shouldBe Version("1.0.1-SNAPSHOT")
        Version("1.0.1").nextSnapshotPatch() shouldBe Version("1.0.2-SNAPSHOT")
        Version("1.1.1").nextSnapshotPatch() shouldBe Version("1.1.2-SNAPSHOT")
        Version("1.0.0-alpha.1").nextSnapshotPatch() shouldBe Version("1.0.1-SNAPSHOT")
        Version("1.0.0-alpha.3").nextSnapshotPatch() shouldBe Version("1.0.1-SNAPSHOT")
        Version("1.1.1-beta.1").nextSnapshotPatch() shouldBe Version("1.1.2-SNAPSHOT")
        Version("2.1.1-beta.1").nextSnapshotPatch() shouldBe Version("2.1.2-SNAPSHOT")
        Version("1.0.0").nextSnapshotMinor() shouldBe Version("1.1.0-SNAPSHOT")
        Version("1.0.1").nextSnapshotMinor() shouldBe Version("1.1.0-SNAPSHOT")
        Version("1.1.1").nextSnapshotMinor() shouldBe Version("1.2.0-SNAPSHOT")
        Version("1.0.0-alpha.1").nextSnapshotMinor() shouldBe Version("1.1.0-SNAPSHOT")
        Version("1.0.0-alpha.3").nextSnapshotMinor() shouldBe Version("1.1.0-SNAPSHOT")
        Version("1.1.1-beta.1").nextSnapshotMinor() shouldBe Version("1.2.0-SNAPSHOT")
        Version("2.1.1-beta.1").nextSnapshotMinor() shouldBe Version("2.2.0-SNAPSHOT")
        Version("1.0.0").nextSnapshotMajor() shouldBe Version("2.0.0-SNAPSHOT")
        Version("1.0.1").nextSnapshotMajor() shouldBe Version("2.0.0-SNAPSHOT")
        Version("1.1.1").nextSnapshotMajor() shouldBe Version("2.0.0-SNAPSHOT")
        Version("1.0.0-alpha.1").nextSnapshotMajor() shouldBe Version("2.0.0-SNAPSHOT")
        Version("1.0.0-alpha.3").nextSnapshotMajor() shouldBe Version("2.0.0-SNAPSHOT")
        Version("1.1.1-beta.1").nextSnapshotMajor() shouldBe Version("2.0.0-SNAPSHOT")
        Version("2.1.1-beta.1").nextSnapshotMajor() shouldBe Version("3.0.0-SNAPSHOT")
    }
}
