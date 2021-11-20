plugins {
    `javiersc-versioning`
    `javiersc-all-projects`
    `javiersc-changelog`
    `javiersc-code-analysis`
    `javiersc-code-formatter`
    `javiersc-docs`
    `binary-compatibility-validator`
    `javiersc-nexus`
    `javiersc-readme-badges-generator`
    id("org.jetbrains.kotlinx.kover") version "0.4.2"
    id("org.sonarqube") version "3.3"
}

sonarqube {
    properties {
        property("sonar.projectKey", "JavierSegoviaCordoba_semantic-versioning-kmp")
        property("sonar.organization", "javiersc")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.coverage.jacoco.xmlReportPaths", "**/build/kover/report.xml")
    }
}

allprojects {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
    }
}
