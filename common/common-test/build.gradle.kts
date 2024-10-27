plugins {
    id("kotlin")
}

dependencies {
    implementation(project(":common:common-kotlin"))
    implementation(libs.kotlinx.coroutines.android)

    api(libs.kotest.runner.junit5)
    api(libs.kotest.assertions.core)
    api(libs.kotest.property)
    api(libs.mockk)
}