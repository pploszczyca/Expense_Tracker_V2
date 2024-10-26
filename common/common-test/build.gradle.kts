plugins {
    id("kotlin")
}

dependencies {
    api(libs.kotest.runner.junit5)
    api(libs.kotest.assertions.core)
    api(libs.kotest.property)
    api(libs.mockk)
}