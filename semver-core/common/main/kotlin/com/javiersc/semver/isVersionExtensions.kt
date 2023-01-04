package com.javiersc.semver

import kotlin.text.RegexOption.IGNORE_CASE

public val Version.isAlpha: Boolean
    get() = stage?.name?.lowercase() == "alpha"

public val Version.isNotAlpha: Boolean
    get() = !isAlpha

public val String.isAlpha: Boolean
    get() = matches(Regex(pattern = """\d+\.\d+\.\d+-alpha\.\d+""", option = IGNORE_CASE))

public val String.isNotAlpha: Boolean
    get() = !isAlpha

public val Version.isBeta: Boolean
    get() = stage?.name?.lowercase() == "beta"

public val Version.isNotBeta: Boolean
    get() = !isBeta

public val String.isBeta: Boolean
    get() = matches(Regex(pattern = """\d+\.\d+\.\d+-beta\.\d+""", option = IGNORE_CASE))

public val String.isNotBeta: Boolean
    get() = !isBeta

public val Version.isDev: Boolean
    get() = stage?.name?.lowercase() == "dev"

public val Version.isNotDev: Boolean
    get() = !isDev

public val String.isDev: Boolean
    get() = matches(Regex(pattern = """\d+\.\d+\.\d+-dev\.\d+""", option = IGNORE_CASE))

public val String.isNotDev: Boolean
    get() = !isDev

public val Version.isRC: Boolean
    get() = stage?.name?.lowercase() == "rc"

public val Version.isNotRC: Boolean
    get() = !isRC

public val String.isRC: Boolean
    get() = matches(Regex(pattern = """\d+\.\d+\.\d+-rc\.\d+""", option = IGNORE_CASE))

public val String.isNotRC: Boolean
    get() = !isRC

public val Version.isSnapshot: Boolean
    get() = stage?.name?.lowercase() == "snapshot"

public val Version.isNotSnapshot: Boolean
    get() = !isSnapshot

public val String.isSnapshot: Boolean
    get() = endsWith("-snapshot", ignoreCase = true)

public val String.isNotSnapshot: Boolean
    get() = !isSnapshot
