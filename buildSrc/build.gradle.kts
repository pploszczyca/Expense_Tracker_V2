plugins {
    `kotlin-dsl`
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib"))
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}
