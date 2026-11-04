// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.gradle)
        classpath(libs.kotlin.gradle.plugin)
        classpath(libs.hilt.android.gradle.plugin)
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle.kts files
    }

    subprojects {
        tasks.withType<Test>().configureEach {
            useJUnitPlatform()
        }
    }
}

plugins {
    id("com.google.dagger.hilt.android") version "2.58" apply false
    id("org.jetbrains.kotlin.jvm") version "2.3.21" apply false
    alias(libs.plugins.compose.compiler) apply false
    id("com.google.devtools.ksp") version "2.3.13" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
