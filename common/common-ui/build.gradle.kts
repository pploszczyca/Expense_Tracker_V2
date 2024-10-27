plugins {
    id("com.android.library")
    id("kotlin-android")
    alias(libs.plugins.compose.compiler)
}

android {
    compileSdk = 35

    defaultConfig {
        minSdk = 26
    }

    namespace = "com.github.pploszczyca.expensetrackerv2.common.common_ui"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }

    kotlinOptions {
        jvmTarget = "21"
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
    implementation(libs.material.icons.extended)

    implementation(libs.material3)
    implementation(libs.material3)
}