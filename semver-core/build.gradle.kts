
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
        browser {
            testTask {
                useMocha {
                    timeout = "30s"
                }
            }
        }
        nodejs {
            testTask {
                useMocha {
                    timeout = "30s"
                }
            }
        }
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
                implementation(libs.jetbrains.kotlin.kotlinTest)
                implementation(libs.jetbrains.kotlinx.kotlinxCoroutinesTest)
                implementation(libs.kotest.kotestAssertionsCore)
                implementation(libs.kotest.kotestProperty)
            }
        }
    }
}
