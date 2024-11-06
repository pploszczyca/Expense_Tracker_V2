plugins {
    id("kotlin")
}

dependencies {
    implementation(libs.opentelemetry.api)
    implementation(libs.opentelemetry.sdk)
    implementation(libs.opentelemetry.tracing)
    implementation(libs.opentelemetry.exporter.otlp)
}