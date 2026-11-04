plugins {
    id("kotlin")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}

dependencies {
    implementation(libs.opentelemetry.api)
    implementation(libs.opentelemetry.sdk)
}