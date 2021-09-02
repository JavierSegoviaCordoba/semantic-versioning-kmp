@file:Suppress("PropertyName", "VariableNaming")

// Catalog name: libs

// [versions]
val coroutines = "1.5.2-native-mt"
val kotest = "4.6.2"

// [libraries]
val jetbrains_kotlin_kotlinTestCommon = "org.jetbrains.kotlin:kotlin-test-common"
val jetbrains_kotlin_kotlinTestJunit = "org.jetbrains.kotlin:kotlin-test-junit"
val jetbrains_kotlinx_kotlinxCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines"
val kotest_kotestAssertionsCore = "io.kotest:kotest-assertions-core:$kotest"

// [bundles]
val testing = jetbrains_kotlin_kotlinTestCommon + kotest_kotestAssertionsCore
