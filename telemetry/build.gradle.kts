plugins {
    id("kotlin")
}

kotlin {
    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.experimental.contextReceivers")
            }
        }
    }
}


dependencies {
    implementation(libs.opentelemetry.api)
    implementation(libs.opentelemetry.sdk)
    implementation(libs.opentelemetry.tracing)
    implementation(libs.opentelemetry.exporter.otlp)
}