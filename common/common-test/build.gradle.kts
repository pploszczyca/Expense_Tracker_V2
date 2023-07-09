import buildSrc.src.main.kotlin.Libs

plugins {
    id("kotlin")
}

dependencies {
    api(Libs.kotestRunnerJUnit5)
    api(Libs.kotestAssertionsCore)
    api(Libs.kotestProperty)
    api(Libs.mockk)
}