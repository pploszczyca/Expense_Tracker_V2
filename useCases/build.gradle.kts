import buildSrc.src.main.kotlin.Libs

plugins {
    id("kotlin")
}

dependencies {
    implementation(project(":domain"))

    implementation(Libs.kotlinxCoroutinesAndroid)

    testImplementation(Libs.kotestRunnerJUnit5)
    testImplementation(Libs.kotestAssertionsCore)
    testImplementation(Libs.kotestProperty)
    testImplementation(Libs.mockk)
}