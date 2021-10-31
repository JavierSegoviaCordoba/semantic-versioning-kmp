
plugins {
    `kotlin-multiplatform`
    `javiersc-kotlin-library`
    `javiersc-publish`
}

kotlin {
    explicitApi()

    iosArm64()
    iosX64()

    jvm()

    linuxX64()
    mingwX64()
    macosX64()

    tvosArm64()
    tvosX64()

    watchosArm64()
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
