pluginManagement {
    val hubdleVersion: String =
        file("$rootDir/gradle/libs.versions.toml")
            .readLines()
            .first { it.contains("hubdle") }
            .split("\"")[1]

    repositories {
        mavenCentral()
        gradlePluginPortal()
        google()
    }

    plugins {
        id("com.javiersc.hubdle.settings") version hubdleVersion
    }
}

plugins {
    id("com.javiersc.hubdle.settings")
}
