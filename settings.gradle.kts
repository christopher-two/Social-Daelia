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
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "social"
include(":app")
include(":auth:api")
include(":auth:impl:firebase")
include(":session:api")
include(":session:impl:datastore")
include(":feature:login:presentation")
include(":feature:login:domain")
include(":core:common")
include(":core:ui")
include(":feature:home:presentation")
