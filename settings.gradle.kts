@file:OptIn(ExperimentalStdlibApi::class)

rootProject.name = providers.gradleProperty("allProjects.name").forUseAtConfigurationTime().get()

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("VERSION_CATALOGS")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        val massiveCatalogs: String by settings

        create("libs") { from("com.javiersc.massive-catalogs:libs-catalog:$massiveCatalogs") }

        create("pluginLibs") {
            from("com.javiersc.massive-catalogs:plugins-catalog:$massiveCatalogs")
        }
    }
}
