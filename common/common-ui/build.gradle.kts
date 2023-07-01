import buildSrc.src.main.kotlin.Libs

plugins {
    id("com.android.library")
    id("kotlin-android")
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 24
    }

    namespace = "com.github.pploszczyca.expensetrackerv2.common.common_ui"

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

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

dependencies {
    implementation(project(":common:common-kotlin"))

    implementation(Libs.material)
    implementation(Libs.composeMaterial)
    implementation(Libs.composeUiToolingPreview)
    implementation(Libs.materialDialogs)
    implementation(Libs.materialIconsExtended)

    implementation(Libs.material3)
    implementation(Libs.material3WindowSizeClass)
}