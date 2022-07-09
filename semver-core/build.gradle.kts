plugins {
    alias(libs.plugins.javiersc.hubdle)
}

hubdle {
    config {
        explicitApi()
        publishing()
    }
    kotlin {
        multiplatform {
            features { coroutines() }

            common {
                test {
                    dependencies {
                        implementation(libs.kotest.kotestProperty)
                    }
                }
            }

            iosArm64()
            iosSimulatorArm64()
            iosX64()
            jvm()
            js {
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
            macosArm64()
            macosX64()
            mingwX64()
            tvosArm64()
            tvosSimulatorArm64()
            tvosX64()
            watchosArm64()
            watchosSimulatorArm64()
            watchosX64()
        }
    }
}
