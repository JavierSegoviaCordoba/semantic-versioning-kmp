package com.javiersc.semanticVersioning

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

    public fun inc(number: Increase): Version =
        when (number) {
            is Increase.Major -> invoke(major.inc(), 0, 0, null, null)
            is Increase.Minor -> invoke(major, minor.inc(), 0, null, null)
            is Increase.Patch -> invoke(major, minor, patch.inc(), null, null)
            is Increase.Stage -> {
                when {
                    number.name.isBlank() -> {
                        semverError("`Increase::Stage::name` must not be empty")
                    }
                    number.name.equals("SNAPSHOT", ignoreCase = true) -> {
                        semverError("`Increase::Stage::name` must not be `SNAPSHOT` or `snapshot`")
                    }
                    stage == null || number.name == stage.name -> {
                        invoke(major, minor, patch, number.name, stage?.num?.inc() ?: 1)
                    }
                    number.name > stage.name -> {
                        invoke(major, minor, patch, number.name, 1)
                    }
                    number.name < stage.name -> {
                        semverError("`Increase::Stage::name` must be higher than previos name")
                    }
                    else -> semverError("This can't happens")
                }
            }
            is Increase.Num -> invoke(major, minor, patch, stage?.name, stage?.num?.inc())
        }

    public fun nextSnapshotMajor(): Version = invoke(major.inc(), 0, 0, "SNAPSHOT", null)

    public fun nextSnapshotMinor(): Version = invoke(major, minor.inc(), 0, "SNAPSHOT", null)

    public fun nextSnapshotPatch(): Version = invoke(major, minor, patch.inc(), "SNAPSHOT", null)

    public fun copy(
        major: Int = this.major,
        minor: Int = this.minor,
        patch: Int? = this.patch,
        stageName: String? = this.stage?.name,
        stageNum: Int? = this.stage?.num,
    ): Version =
        Version(
            major = major,
            minor = minor,
            patch = patch,
            stageName = stageName,
            stageNum = if (stageName.equals("SNAPSHOT", ignoreCase = true)) null else stageNum
        )

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
            patch: Int?,
            stageName: String?,
            stageNum: Int?,
        ): Version = Version(buildVersion(major, minor, patch, stageName, stageNum))

        public fun safe(
            major: Int,
            minor: Int,
            patch: Int?,
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
                name > other.name -> 1
                name < other.name -> -1
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
            public val regex: Regex = """([a-zA-Z]+(\.\d+)|\bSNAPSHOT\b)?$""".toRegex()

            public operator fun invoke(stage: String): Stage = Stage(stage)

            public operator fun invoke(name: String, num: Int?): Stage =
                if (num != null) Stage("$name.$num") else Stage(name)
        }
    }

    public sealed interface Increase {
        public object Major : Increase
        public object Minor : Increase
        public object Patch : Increase
        public data class Stage(val name: String) : Increase
        public object Num : Increase
    }
}

private fun String.red() = "$RED$this$RESET"

private fun checkFullVersion(version: String) {
    checkVersion(version.matches(Version.regex)) {
        """|The version is not semantic, rules:
           |  - `major` and `minor` are required, rest are optional
           |  - `num` is required if `stage` is present and it is not snapshot
           |
           |Current version: $version
           |
           |Samples of semantic versions:
           |1.0
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
    checkVersion(stage.matches(Version.Stage.regex)) {
        """`|stage` provided has an incorrect format
            | 
            |Samples
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
    if (patch != null) append(".$patch")
    if (!stageName.isNullOrBlank()) {
        append("-")
        append(Version.Stage(stageName, stageNum).toString())
    }
}

private const val RED = "\u001b[31m"
private const val RESET = "\u001B[0m"
