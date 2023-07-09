import buildSrc.src.main.kotlin.Libs

plugins {
    id("kotlin")
}

dependencies {
    implementation(Libs.kotlinxCoroutinesAndroid)

    testImplementation(project(":common:common-test"))
}