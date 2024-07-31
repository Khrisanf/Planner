pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS) // Измените на PREFER_SETTINGS или PREFER_PROJECT в зависимости от вашего предпочтения
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "MyApplication2"
include(":app")
