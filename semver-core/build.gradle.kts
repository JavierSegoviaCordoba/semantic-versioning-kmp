
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

    js(BOTH) {
        browser()
        nodejs()
    }

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
                implementation(libs.javiersc.runBlocking.suspendTest)
                implementation(libs.jetbrains.kotlin.kotlinTest)
                implementation(libs.kotest.kotestAssertionsCore)
                implementation(libs.kotest.kotestProperty)
            }
        }
    }
}
