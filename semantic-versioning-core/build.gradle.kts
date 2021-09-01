
plugins {
    `javiersc-kotlin-multiplatform`
    `javiersc-publish-kotlin-multiplatform`
}

kotlin {
    explicitApi()

    iosArm64()
    iosSimulatorArm64()
    iosX64()

    jvm()

    linuxX64()

    mingwX64()

    macosArm64()
    macosX64()

    tvosArm64()
    tvosSimulatorArm64()
    tvosX64()

    watchosArm64()
    watchosSimulatorArm64()
    watchosX64()

    sourceSets {
        commonTest {
            dependencies {
                implementation(libs.jetbrains.kotlin.kotlinTestCommon)
                implementation(libs.jetbrains.kotlin.kotlinTestJunit)
                implementation(libs.kotest.kotestAssertionsCore)
            }
        }
    }
}
