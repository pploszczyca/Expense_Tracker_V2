import org.gradle.api.initialization.resolve.RepositoriesMode

include(":features:category-settings")


include(":features:expense-statistics")


include(":features:expense-form")


include(":common:common-kotlin")


include(":common:common-ui")


include(":navigation:navigation-contract")


include(":navigation")


dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Expense Tracker V2"

include(":app")
include(":domain")
include(":useCases")
include(":database")
