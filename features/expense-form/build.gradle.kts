import buildSrc.src.main.kotlin.Libs

plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 26
    }

    namespace = "com.github.pploszczyca.expensetrackerv2.features.expense_form"

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
    implementation(project(":domain"))
    implementation(project(":useCases"))
    implementation(project(":common:common-ui"))
    implementation(project(":common:common-kotlin"))
    implementation(project(":navigation:navigation-contract"))

    implementation(Libs.androidxCore)
    implementation(Libs.lifecycleViewModelKtx)
    implementation(Libs.material)
    implementation(Libs.composeMaterial)
    implementation(Libs.composeUiToolingPreview)
    implementation(Libs.materialDialogs)
    implementation(Libs.materialIconsExtended)

    implementation(Libs.material3)
    implementation(Libs.material3WindowSizeClass)

    implementation(Libs.kotlinxCoroutinesAndroid)

    implementation(Libs.hiltAndroid)
    kapt(Libs.hiltAndroidCompiler)

    testImplementation(Libs.kotestRunnerJUnit5)
    testImplementation(Libs.kotestAssertionsCore)
    testImplementation(Libs.kotestProperty)
    testImplementation(Libs.mockk)
}