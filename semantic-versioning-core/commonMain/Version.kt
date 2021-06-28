package com.javiersc.semanticVersioning

import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

public class Version private constructor(public val value: String) : Comparable<Version> {

    init {
        checkFullVersion(value)
    }

    public val major: Int = preStage.split(".").first().toInt()

    public val minor: Int = preStage.split(".")[1].toInt()

    public val patch: Int? = preStage.split(".").getOrNull(2)?.toInt()

    public val stage: Stage? = stageAndNum?.let { Stage(it) }

    @Suppress("ComplexMethod")
    override fun compareTo(other: Version): Int =
        when {
            major > other.major -> 1
            major < other.major -> -1
            minor > other.minor -> 1
            minor < other.minor -> -1
            patch == null && other.patch != null -> 1
            patch != null && other.patch == null -> -1
            patch != null && other.patch != null && patch > other.patch -> 1
            patch != null && other.patch != null && patch < other.patch -> -1
            stage == null && other.stage != null -> 1
            stage != null && other.stage == null -> -1
            stage != null && other.stage != null && stage > other.stage -> 1
            stage != null && other.stage != null && stage < other.stage -> -1
            else -> 0
        }

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

        public val regex: Regex = "^(\\d+.\\d+)(.\\d+)?(-[a-zA-Z]+.\\d+)?\$".toRegex()

        public operator fun invoke(value: String): Version {
            checkFullVersion(value)
            return Version(value)
        }

        public operator fun invoke(version: String, stage: String): Version {
            checkShortVersion(version)
            return if (stage.isNotBlank()) {
                checkStage(stage)
                Version("$version-$stage")
            } else Version(version)
        }

        public operator fun invoke(
            major: Int,
            minor: Int,
            patch: Int?,
            stage: String?,
            num: Int?,
        ): Version {
            val version = buildString {
                append(major)
                append(".")
                append(minor)
                patch?.let { append(".$patch") }
                stage?.let {
                    checkVersion(stage.matches(wordRegex)) { "`stage` should be a word" }
                    checkVersion(num != null) {
                        "`num` should be provided if `patch` is provided".red()
                    }
                    append("-$stage.$num")
                }
            }
            return Version(version)
        }
    }

    private val preStage: String
        get() = value.split("-").first()

    private val stageAndNum: String?
        get() = value.split("-").getOrNull(1)

    public class Stage private constructor(private val value: String) : Comparable<Stage> {

        init {
            checkStage(value)
        }

        private val name: String = value.split(".").first()

        private val num: Int = value.split(".")[1].toInt()

        override fun compareTo(other: Stage): Int =
            when {
                name > other.name -> 1
                name < other.name -> -1
                num > other.num -> 1
                num < other.num -> -1
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
            public val regex: Regex = "^[a-zA-Z]+.\\d+\$".toRegex()

            public operator fun invoke(stage: String): Stage {
                checkVersion(stage.contains(".")) { "`stage` format is incorrect" }
                val localStage = stage.split(".")
                checkVersion(localStage.first().matches(wordRegex)) { "`stage` should be a word" }
                checkVersion(localStage.getOrNull(1) != null && localStage[1].all(Char::isDigit)) {
                    "`num` should be a number"
                }
                return Stage("${localStage.first()}.${localStage[1]}")
            }

            public operator fun invoke(stage: String, num: Int): Stage {
                checkVersion(stage.matches(wordRegex)) { "`stage` should be a word" }
                return Stage("$stage.$num")
            }
        }
    }
}

private fun String.red() = "$RED$this$RESET"

private val shortVersionRegex = "^(\\d+.\\d+)(.\\d+)?\$".toRegex()
private val wordRegex = "^[a-zA-Z]+$".toRegex()

private fun checkFullVersion(version: String) {
    checkVersion(version.matches(Version.regex)) {
        val headerError =
            "Version format is incorrect, " +
                "major and minor are required, " +
                "rest are optional, " +
                "except num which is required if stage is present"

        """|$headerError
               |
               |Samples of correct versions:
               |1.0
               |1.0.0
               |1.0-alpha.1
               |1.0.0-alpha.1
               |12.23.34-alpha.45
            """
            .trimMargin()
            .red()
    }
}

private fun checkShortVersion(version: String) {
    checkVersion(version.matches(shortVersionRegex)) {
        """|`version` provided has an incorrect format
           |
           |Samples:
           | 1.0.0
           | 1.0
           | 12.23.34
           | 12.23
        """.trimMargin()
    }
}

private fun checkStage(stage: String) {
    checkVersion(stage.matches(Version.Stage.regex)) {
        """`|stage` provided has an incorrect format
            | 
            |Samples
            |alpha.1
            |beta.23
        """
            .trimMargin()
            .red()
    }
}

public class VersionException(override val message: String) : Exception(message)

@OptIn(ExperimentalContracts::class)
private inline fun checkVersion(value: Boolean, lazyMessage: () -> Any) {
    contract { returns() implies value }
    if (!value) {
        val message = lazyMessage()
        throw VersionException(message.toString())
    }
}

private const val RED = "\u001b[31m"
private const val RESET = "\u001B[0m"
