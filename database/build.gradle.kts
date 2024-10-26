plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
}

android {
    compileSdk = 34

    defaultConfig {
        minSdk = 26
    }

    namespace = "com.github.pploszczyca.expensetrackerv2.database"

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

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.room.runtime)
    annotationProcessor(libs.room.compiler)
    kapt(libs.room.compiler)
    implementation(libs.room.ktx)
}

kapt.correctErrorTypes = true
