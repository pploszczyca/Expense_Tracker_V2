import buildSrc.src.main.kotlin.Libs

plugins {
    id("kotlin")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common:common-kotlin"))

    implementation(Libs.kotlinxCoroutinesAndroid)

    testImplementation(project(":common:common-test"))
}