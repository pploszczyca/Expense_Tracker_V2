import buildSrc.src.main.kotlin.Libs

plugins {
    id("kotlin")
}

dependencies {
    testImplementation(project(":common:common-test"))
}