package com.javiersc.semver

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

public class Version private constructor(public val value: String) : Comparable<Version> {

    init {
        checkFullVersion(value)
    }

    public val major: Int = preStage.split(".").first().toInt()

    public val minor: Int = preStage.split(".")[1].toInt()

    public val patch: Int = preStage.split(".").getOrNull(2)?.toInt() ?: 0

    public val stage: Stage? = stageAndNum?.let { Stage(it) }

    @Suppress("ComplexMethod")
    override fun compareTo(other: Version): Int =
        when {
            major > other.major -> 1
            major < other.major -> -1
            minor > other.minor -> 1
            minor < other.minor -> -1
            patch > other.patch -> 1
            patch < other.patch -> -1
            stage == null && other.stage != null -> 1
            stage != null && other.stage == null -> -1
            stage != null && other.stage != null && stage > other.stage -> 1
            stage != null && other.stage != null && stage < other.stage -> -1
            else -> 0
        }

    @Suppress("ComplexMethod")
    public fun inc(number: Increase? = null, stageName: String = ""): Version {
        val incNum = if (stageName.lowercase() == "snapshot") null else 1
        val nextVersion: Version =
            when {
                number == null && stageName.isBlank() && !stage?.name.isNullOrBlank() -> {
                    invoke(major, minor, patch, null, null)
                }
                number == null && stageName.isBlank() -> {
                    invoke(major, minor, patch.inc(), null, null)
                }
                number == null && stageName.isNotBlank() && stage?.name.isNullOrBlank() -> {
                    invoke(major, minor, patch.inc(), stageName, incNum)
                }
                number == null && stageName.isNotBlank() && stageName == stage?.name -> {
                    invoke(major, minor, patch, stageName, stage.num?.inc())
                }
                number == null && stageName.isNotBlank() && stageName != stage?.name -> {
                    invoke(major, minor, patch, stageName, incNum)
                }
                number is Increase.Major && stageName.isBlank() -> {
                    invoke(major.inc(), 0, 0, null, null)
                }
                number is Increase.Minor && stageName.isBlank() -> {
                    invoke(major, minor.inc(), 0, null, null)
                }
                number is Increase.Patch && stageName.isBlank() -> {
                    invoke(major, minor, patch.inc(), null, null)
                }
                number is Increase.Major && stageName.isNotBlank() -> {
                    invoke(major.inc(), 0, 0, stageName, incNum)
                }
                number is Increase.Minor && stageName.isNotBlank() -> {
                    invoke(major, minor.inc(), 0, stageName, incNum)
                }
                number is Increase.Patch && stageName.isNotBlank() -> {
                    invoke(major, minor, patch.inc(), stageName, incNum)
                }
                else -> null
            } ?: semverError("There were an error configuring the version")

        if (nextVersion < this) {
            semverError("Next version ($nextVersion) should be higher than the current one ($this)")
        }
        return nextVersion
    }

    public fun copy(
        major: Int = this.major,
        minor: Int = this.minor,
        patch: Int = this.patch,
        stageName: String? = this.stage?.name,
        stageNum: Int? = this.stage?.num,
    ): Version =
        Version(
            major = major,
            minor = minor,
            patch = patch,
            stageName = stageName,
            stageNum = if (stageName.equals("SNAPSHOT", ignoreCase = true)) null else stageNum)

    override fun equals(other: Any?): Boolean {
        val otherVersion = other as? Version
        return when {
            otherVersion == null -> false
            compareTo(otherVersion) == 0 -> true
            else -> false
        }
    }

    override fun toString(): String = value

    override fun hashCode(): Int = value.hashCode()

    public companion object {
        public val regex: Regex = VersionFormat.Default.regex

        public val scopeRegex: Regex = VersionFormat.Default.scope

        public operator fun invoke(value: String): Version = Version(value)

        public fun safe(value: String): Result<Version> = runCatching { Version(value) }

        public operator fun invoke(version: String, stage: String?): Version =
            if (stage.isNullOrBlank()) Version(version) else Version("$version-$stage")

        public fun safe(version: String, stage: String?): Result<Version> = runCatching {
            if (stage.isNullOrBlank()) Version(version) else Version("$version-$stage")
        }

        public operator fun invoke(
            major: Int,
            minor: Int,
            patch: Int,
            stageName: String?,
            stageNum: Int?,
        ): Version = Version(buildVersion(major, minor, patch, stageName, stageNum))

        public fun safe(
            major: Int,
            minor: Int,
            patch: Int,
            stageName: String?,
            stageNum: Int?,
        ): Result<Version> = runCatching { Version(major, minor, patch, stageName, stageNum) }
    }

    private val preStage: String
        get() = value.split("-").first()

    private val stageAndNum: String?
        get() = value.split("-").getOrNull(1)

    public class Stage private constructor(private val value: String) : Comparable<Stage> {

        init {
            checkStage(value)
        }

        public val name: String = value.split(".").first()

        public val num: Int? = value.split(".").getOrNull(1)?.toInt()

        @Suppress("ComplexMethod")
        override fun compareTo(other: Stage): Int =
            when {
                name.lowercase() > other.name.lowercase() -> 1
                name.lowercase() < other.name.lowercase() -> -1
                num != null && other.num == null -> 1
                num == null && other.num != null -> -1
                num != null && other.num != null && num > other.num -> 1
                num != null && other.num != null && num < other.num -> -1
                else -> 0
            }

        override fun equals(other: Any?): Boolean {
            val otherStage = other as? Stage
            return when {
                otherStage == null -> false
                compareTo(otherStage) == 0 -> true
                else -> false
            }
        }

        override fun toString(): String = value

        override fun hashCode(): Int = value.hashCode()

        public companion object {
            public val stageRegex: Regex = VersionFormat.Default.stage

            public operator fun invoke(stage: String): Stage = Stage(stage)

            public operator fun invoke(name: String, num: Int?): Stage =
                if (num != null) Stage("$name.$num") else Stage(name)
        }
    }

    public sealed interface Increase {
        public object Major : Increase

        public object Minor : Increase

        public object Patch : Increase
    }
}

private fun String.red() = "$RED$this$RESET"

private fun checkFullVersion(version: String) {
    checkVersion(version.matches(Version.regex)) {
        """|The version is not semantic, rules:
           |  - `major`, `minor` and `patch` are required, rest are optional
           |  - `num` is required if `stage` is present and it is not snapshot
           |
           |Current version: $version
           |
           |Samples of semantic versions:
           |1.0.0
           |1.0-alpha.1
           |1.0.0-SNAPSHOT
           |1.0.0-alpha.1
           |12.23.34-alpha.45
           |12.23.34-SNAPSHOT
           |
        """
            .trimMargin()
            .red()
    }
}

private fun checkStage(stage: String) {
    checkVersion(stage.matches(Version.Stage.stageRegex)) {
        """|`stage` provided has an incorrect format
           | 
           |Samples of stages:
           |alpha.1
           |beta.23
           |SNAPSHOT
        """
            .trimMargin()
            .red()
    }
}

public class SemanticVersionException(override val message: String) : Exception(message)

internal fun semverError(message: String): Nothing = throw SemanticVersionException(message)

@OptIn(ExperimentalContracts::class)
private inline fun checkVersion(value: Boolean, lazyMessage: () -> Any) {
    contract { returns() implies value }
    if (!value) semverError(lazyMessage().toString())
}

private fun buildVersion(
    major: Int,
    minor: Int,
    patch: Int?,
    stageName: String?,
    stageNum: Int?,
): String = buildString {
    append(major)
    append(".")
    append(minor)
    append(".")
    append(patch)
    if (!stageName.isNullOrBlank()) {
        append("-")
        append(Version.Stage(stageName, stageNum).toString())
    }
}

private const val RED = "\u001b[31m"
private const val RESET = "\u001B[0m"
