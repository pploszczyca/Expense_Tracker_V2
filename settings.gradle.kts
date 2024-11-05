listOf(
    ":app",
    ":domain",
    ":useCases",
    ":database",
    ":common:common-kotlin",
    ":common:common-ui",
    ":common:common-test",
    ":features:main",
    ":features:category-settings",
    ":features:expense-statistics",
    ":features:expense-form",
    ":navigation:navigation-contract",
    ":navigation",
).forEach {
    include(it)
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Expense Tracker V2"

