plugins {
    id("kotlin")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-receivers")
    }
}

dependencies {
    implementation(project(":common:common-kotlin"))
    implementation(libs.kotlinx.coroutines.android)

    implementation(project(":telemetry"))

    api(libs.kotest.runner.junit5)
    api(libs.kotest.assertions.core)
    api(libs.kotest.property)
    api(libs.mockk)
}