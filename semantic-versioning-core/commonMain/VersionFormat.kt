package com.javiersc.semanticVersioning

/**
 * Samples:
 *
 * - Normal: `1.0.0-alpha.01`
 * - NoDot: `1.0.0-alpha01`
 */
internal enum class VersionFormat(val regex: Regex) {
    Default("""^(\d+.\d+)(\.\d+)?(-[a-zA-Z]+(\.\d+))?$""".toRegex()),
    NoDot("""^(\d+.\d+)(\.\d+)?(-[a-zA-Z]+(\d+))?$""".toRegex()),
}
