package com.javiersc.semver

import kotlin.text.RegexOption.IGNORE_CASE

/**
 * Samples:
 * - Default: `1.0.0-alpha.01`
 */
internal enum class VersionFormat(val scope: Regex, val stage: Regex) {
    Default(
        Regex("""^(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)"""),
        Regex("""(?!SNAPSHOT\.\d)([a-zA-Z]+(\.\d+)|\bSNAPSHOT\b)""", IGNORE_CASE));

    val regex: Regex =
        Regex(
            """^(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)(-(?!SNAPSHOT\.\d)([a-zA-Z]+(\.\d+)|\bSNAPSHOT\b))?$""",
            IGNORE_CASE)
}
