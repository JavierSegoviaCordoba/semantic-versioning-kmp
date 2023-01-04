package com.javiersc.semver

import com.javiersc.semver.Version.Increase.Major
import com.javiersc.semver.Version.Increase.Minor
import com.javiersc.semver.Version.Increase.Patch
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.comparables.shouldBeGreaterThan
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.choice
import io.kotest.property.arbitrary.constant
import io.kotest.property.arbitrary.positiveInt
import io.kotest.property.checkAll
import io.kotest.property.forAll
import kotlin.test.Test
import kotlinx.coroutines.test.runTest

class VersionTest {

    private val major = Arb.positiveInt(30)
    private val minor = Arb.positiveInt(30)
    private val patch = Arb.positiveInt(30)
    private val stageName =
        Arb.choice(
            Arb.constant("alpha"),
            Arb.constant("beta"),
            Arb.constant("rc"),
            Arb.constant("SNAPSHOT"),
            Arb.constant("zasca"),
            Arb.constant(null)
        )
    private val num =
        Arb.choice(
            Arb.positiveInt(30),
            Arb.constant(null),
        )

    private val versionArbitrary: Arb<Version> = arbitrary {
        val major = major.bind()
        val minor = minor.bind()
        val patch = patch.bind()
        val stageName = stageName.bind()
        val num = num.bind()
        val stage: String =
            when {
                stageName.equals("SNAPSHOT", ignoreCase = true) -> "-$stageName"
                stageName == null || num == null -> ""
                else -> "-$stageName.$num"
            }
        Version("$major.$minor.$patch$stage")
    }

    @Test
    fun major_comparator() = runTest {
        forAll(versionArbitrary, versionArbitrary) { a: Version, b: Version ->
            if (a.major > b.major) a > b else true
        }
    }

    @Test
    fun minor_comparator() = runTest {
        forAll(versionArbitrary, versionArbitrary) { a: Version, b: Version ->
            if ((a.major == b.major) && (a.minor > b.minor)) a > b else true
        }
    }

    @Test
    fun patch_comparator() = runTest {
        forAll(versionArbitrary, versionArbitrary) { a: Version, b: Version ->
            if ((a.major == b.major) && (a.minor == b.minor) && (a.patch > b.patch)) a > b else true
        }
    }

    @Test
    fun stage_name_comparator() = runTest {
        forAll(versionArbitrary, versionArbitrary) { a: Version, b: Version ->
            if (
                (a.major == b.major) &&
                    (a.minor == b.minor) &&
                    (a.patch == b.patch) &&
                    (a.stage?.name != null) &&
                    (b.stage?.name != null) &&
                    (a.stage!!.name > b.stage!!.name)
            )
                a > b
            else true
        }
    }

    @Test
    fun stage_num_comparator() = runTest {
        forAll(versionArbitrary, versionArbitrary) { a: Version, b: Version ->
            if (
                (a.major == b.major) &&
                    (a.minor == b.minor) &&
                    (a.patch == b.patch) &&
                    (a.stage?.name != null) &&
                    (b.stage?.name != null) &&
                    (a.stage!!.name == b.stage!!.name) &&
                    (a.stage?.num != null) &&
                    (b.stage?.num != null) &&
                    (a.stage!!.num!! > b.stage!!.num!!)
            )
                a > b
            else true
        }
    }

    @Test
    fun wrong_versions() = runTest {
        checkAll(major, minor, patch, stageName, num) { major, minor, patch, stageName, num ->
            when {
                stageName.equals("SNAPSHOT", true) && num != null -> {
                    shouldThrow<SemanticVersionException> {
                        Version(major, minor, patch, stageName, num)
                    }
                }
                stageName.equals("SNAPSHOT", true) && num == null -> {
                    Version(major, minor, patch, stageName, num)
                }
                stageName != null && num == null -> {
                    shouldThrow<SemanticVersionException> {
                        Version(major, minor, patch, stageName, num)
                    }
                }
                else -> Version(major, minor, patch, stageName, num)
            }
        }
    }

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
    fun order() {
        val actualList =
            listOf(
                Version("0.1.0-SNAPSHOT"),
                Version("0.1.0-rc.13"),
                Version("0.1.0-rc.12"),
                Version("0.1.0-rc.11"),
                Version("0.1.0-rc.10"),
                Version("0.1.0-rc.9"),
                Version("0.1.0-rc.8"),
                Version("0.1.0-rc.7"),
                Version("0.1.0-rc.6"),
                Version("0.1.0-rc.5"),
                Version("0.1.0-rc.4"),
                Version("0.1.0-rc.3"),
                Version("0.1.0-rc.2"),
                Version("0.1.0-rc.1"),
                Version("0.1.0-beta.5"),
                Version("0.1.0-beta.4"),
                Version("0.1.0-beta.3"),
                Version("0.1.0-beta.2"),
                Version("0.1.0-beta.1"),
                Version("0.1.0-alpha.23"),
                Version("0.1.0-alpha.22"),
                Version("0.1.0-alpha.21"),
                Version("0.1.0-alpha.20"),
                Version("0.1.0-alpha.19"),
                Version("0.1.0-alpha.18"),
                Version("0.1.0-alpha.17"),
                Version("0.1.0-alpha.16"),
                Version("0.1.0-alpha.15"),
                Version("0.1.0-alpha.14"),
                Version("0.1.0-alpha.13"),
                Version("0.1.0-alpha.12"),
                Version("0.1.0-alpha.11"),
                Version("0.1.0-alpha.10"),
                Version("0.1.0-alpha.9"),
                Version("0.1.0-alpha.8"),
                Version("0.1.0-alpha.7"),
                Version("0.1.0-alpha.6"),
                Version("0.1.0-alpha.5"),
                Version("0.1.0-alpha.4"),
                Version("0.1.0-alpha.3"),
                Version("0.1.0-alpha.2"),
                Version("0.1.0-alpha.1"),
            )

        actualList shouldBe actualList.sortedDescending()
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
        Version("1.0.0-SNAPSHOT") shouldBeGreaterThan Version("1.0.0-alpha.1")
        Version("1.0.0-SNAPSHOT") shouldBeGreaterThan Version("1.0.0-rc.1")
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
        Version("1.1.0").inc(stageName = "SNAPSHOT") shouldBe Version("1.1.1-SNAPSHOT")
        Version("1.1.0").inc(stageName = "snapshot") shouldBe Version("1.1.1-SNAPSHOT")
        Version("1.1.0-SNAPSHOT").inc(stageName = "tatata") shouldBe Version("1.1.0-tatata.1")
        Version("1.1.0-snapshot").inc(stageName = "tatata") shouldBe Version("1.1.0-tatata.1")
        shouldThrow<SemanticVersionException> { Version("1.1.0-SNAPSHOT").inc(stageName = "rc") }
        shouldThrow<SemanticVersionException> { Version("1.1.0-rc.1").inc(stageName = "beta") }
        shouldThrow<SemanticVersionException> { Version("1.1.0-beta.1").inc(stageName = "alpha") }
    }

    @Test
    fun increase_stage_and_patch() {
        Version("1.0.0").inc(Patch, "alpha") shouldBe Version("1.0.1-alpha.1")
        Version("1.0.0-alpha.1").inc(Patch, "alpha") shouldBe Version("1.0.1-alpha.1")
        Version("1.0.0-alpha.1").inc(Patch, "") shouldBe Version("1.0.1")
        Version("1.0.0-alpha.1").inc(Patch, "beta") shouldBe Version("1.0.1-beta.1")
        Version("1.1.0-beta.1").inc(Patch, "rc") shouldBe Version("1.1.1-rc.1")
        Version("1.1.0").inc(Patch, "rc") shouldBe Version("1.1.1-rc.1")
        Version("1.1.0-SNAPSHOT").inc(Patch, "rc") shouldBe Version("1.1.1-rc.1")
        Version("1.1.0-SNAPSHOT").inc(Patch) shouldBe Version("1.1.1")
        Version("1.1.0-beta.1").inc(Patch, "alpha") shouldBe Version("1.1.1-alpha.1")
        Version("1.0.0").inc(Patch, "snapshot") shouldBe Version("1.0.1-SNAPSHOT")
        Version("1.0.0").inc(Patch, "SNAPSHOT") shouldBe Version("1.0.1-SNAPSHOT")
    }

    @Test
    fun increase_stage_and_minor() {
        Version("1.0.0").inc(Minor, "alpha") shouldBe Version("1.1.0-alpha.1")
        Version("1.0.0-alpha.1").inc(Minor, "alpha") shouldBe Version("1.1.0-alpha.1")
        Version("1.0.0-alpha.1").inc(Minor, "") shouldBe Version("1.1.0")
        Version("1.0.0-alpha.1").inc(Minor, "beta") shouldBe Version("1.1.0-beta.1")
        Version("1.1.0-beta.1").inc(Minor, "rc") shouldBe Version("1.2.0-rc.1")
        Version("1.1.0").inc(Minor, "rc") shouldBe Version("1.2.0-rc.1")
        Version("1.1.0-SNAPSHOT").inc(Minor, "rc") shouldBe Version("1.2.0-rc.1")
        Version("1.1.0-SNAPSHOT").inc(Minor) shouldBe Version("1.2.0")
        Version("1.1.0-beta.1").inc(Minor, "alpha") shouldBe Version("1.2.0-alpha.1")
        Version("1.0.0").inc(Minor, "snapshot") shouldBe Version("1.1.0-SNAPSHOT")
        Version("1.0.0").inc(Minor, "SNAPSHOT") shouldBe Version("1.1.0-SNAPSHOT")
    }

    @Test
    fun increase_stage_and_major() {
        Version("1.0.0").inc(Major, "alpha") shouldBe Version("2.0.0-alpha.1")
        Version("1.0.0-alpha.1").inc(Major, "alpha") shouldBe Version("2.0.0-alpha.1")
        Version("1.0.0-alpha.1").inc(Major, "") shouldBe Version("2.0.0")
        Version("1.0.0-alpha.1").inc(Major, "beta") shouldBe Version("2.0.0-beta.1")
        Version("1.1.0-beta.1").inc(Major, "rc") shouldBe Version("2.0.0-rc.1")
        Version("1.1.0").inc(Major, "rc") shouldBe Version("2.0.0-rc.1")
        Version("1.1.0-SNAPSHOT").inc(Major, "rc") shouldBe Version("2.0.0-rc.1")
        Version("1.1.0-SNAPSHOT").inc(Major) shouldBe Version("2.0.0")
        Version("1.1.0-beta.1").inc(Major, "alpha") shouldBe Version("2.0.0-alpha.1")
        Version("1.0.0").inc(Major, "snapshot") shouldBe Version("2.0.0-SNAPSHOT")
        Version("1.0.0").inc(Major, "SNAPSHOT") shouldBe Version("2.0.0-SNAPSHOT")
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
        Version("1.0.19-beta.5").inc(Patch, stageName = "beta") shouldBe Version("1.0.20-beta.1")
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
}
