import buildSrc.src.main.kotlin.Libs

plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = 33

    defaultConfig {
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "3.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

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

    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }

    defaultConfig {
        applicationId = "com.example.expensetrackerv2"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }

    namespace = "com.example.expensetrackerv2"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":useCases"))
    implementation(project(":database"))
    implementation(project(":navigation:navigation-contract"))
    implementation(project(":common:common-ui"))
    implementation(project(":common:common-kotlin"))

    implementation(project(":features:expense-form"))

    implementation(Libs.androidxCore)
    implementation(Libs.appcompat)
    implementation(Libs.material)
    implementation(Libs.composeUi)
    implementation(Libs.composeMaterial)
    implementation(Libs.composeUiToolingPreview)
    implementation(Libs.lifecycleRuntimeKtx)
    implementation(Libs.activityCompose)
    androidTestImplementation(Libs.androidTestJUnit)
    androidTestImplementation(Libs.espressoCore)
    androidTestImplementation(Libs.composeUiTestJUnit4)
    debugImplementation(Libs.composeUiTooling)
    implementation(Libs.navigationCompose)
    implementation(Libs.materialIconsExtended)

    implementation(Libs.kotlinxCoroutinesAndroid)

    implementation(Libs.hiltAndroid)
    kapt(Libs.hiltAndroidCompiler)

    implementation(Libs.hiltNavigationCompose)

    implementation(Libs.material3)
    implementation(Libs.material3WindowSizeClass)

    testImplementation(Libs.kotestRunnerJUnit5)
    testImplementation(Libs.kotestAssertionsCore)
    testImplementation(Libs.kotestProperty)
    testImplementation(Libs.mockk)
}

kapt {
    correctErrorTypes = true
}
