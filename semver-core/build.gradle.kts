plugins {
    alias(libs.plugins.javiersc.hubdle)
}

hubdle {
    config {
        explicitApi()
        languageSettings {
            experimentalCoroutinesApi()
        }
        publishing()
    }
    kotlin {
        multiplatform {
            features {
                coroutines()
            }

            common {
                test {
                    dependencies {
                        implementation(kotestProperty())
                    }
                }
            }

            apple {
                ios {
                    iosArm64()
                    iosSimulatorArm64()
                    iosX64()
                }
                macos {
                    macosArm64()
                    macosX64()
                }
                tvos {
                    tvosArm64()
                    tvosSimulatorArm64()
                    tvosX64()
                }
                watchos {
                    watchosArm64()
                    watchosSimulatorArm64()
                    watchosX64()
                }
            }
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
            linux {
                linuxX64()
            }
            mingw {
                mingwX64()
            }
        }
    }
}
