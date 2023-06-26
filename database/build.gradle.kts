import buildSrc.src.main.kotlin.Libs

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 24
    }

    namespace = "com.example.expensetrackerv2"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":useCases"))

    implementation(Libs.kotlinxCoroutinesAndroid)
    implementation(Libs.roomRuntime)
    annotationProcessor(Libs.roomCompiler)
    kapt(Libs.roomCompiler)
    implementation(Libs.roomKtx)
}

kapt.correctErrorTypes = true
