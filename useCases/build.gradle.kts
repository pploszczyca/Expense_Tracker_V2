plugins {
    id("kotlin")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":common:common-kotlin"))

    implementation(libs.kotlinx.coroutines.android)

    testImplementation(project(":common:common-test"))
}