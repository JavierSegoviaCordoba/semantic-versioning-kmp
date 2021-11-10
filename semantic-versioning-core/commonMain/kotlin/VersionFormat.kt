@file:Suppress("MaxLineLength")

package com.javiersc.semanticVersioning

/**
 * Samples:
 *
 * - Normal: `1.0.0-alpha.01`
 * - NoDot: `1.0.0-alpha01`
 */
internal enum class VersionFormat(val regex: Regex) {
    Default(
        """^(0|[1-9]\d*)\.(0|[1-9]\d*)\.(0|[1-9]\d*)(-(?!(?i)SNAPSHOT\.\d)([a-zA-Z]+(\.\d+)|\b(?i)SNAPSHOT\b)?)?${'$'}""".toRegex()
    )
}
