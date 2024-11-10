plugins {
    id("kotlin")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-receivers")
    }
}

dependencies {
    implementation(libs.opentelemetry.api)
    implementation(libs.opentelemetry.sdk)
}