import buildSrc.src.main.kotlin.Libs

plugins {
    id("kotlin")
}

dependencies {
    testImplementation(Libs.kotestRunnerJUnit5)
    testImplementation(Libs.kotestAssertionsCore)
    testImplementation(Libs.kotestProperty)
}