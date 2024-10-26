plugins {
    id("com.android.library")
    id("kotlin-android")
    alias(libs.plugins.compose.compiler)
}

android {
    compileSdk = 34

    defaultConfig {
        minSdk = 26
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

}

dependencies {
    implementation(project(":common:common-kotlin"))

    implementation(libs.material)
    implementation(libs.compose.material)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.material.dialogs)
    implementation(libs.material.icons.extended)

    implementation(libs.material3)
    implementation(libs.material3)
}