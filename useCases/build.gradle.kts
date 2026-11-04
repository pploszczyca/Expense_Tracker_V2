plugins {
    id("kotlin")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xcontext-parameters")
    }
}


dependencies {
    implementation(project(":domain"))
    implementation(project(":common:common-kotlin"))
    implementation(project(":telemetry"))

    implementation(libs.kotlinx.coroutines.android)

    testImplementation(project(":common:common-test"))
}