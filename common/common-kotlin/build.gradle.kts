plugins {
    id("kotlin")
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)

    testImplementation(project(":common:common-test"))
}