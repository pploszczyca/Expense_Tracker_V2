package com.github.pploszczyca.expensetrackerv2.telemetry

import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.exporter.otlp.trace.OtlpGrpcSpanExporter
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.trace.SdkTracerProvider
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor

object TelemetryDI {
    private lateinit var openTelemetry: OpenTelemetry

    fun init() {
        val spanExporter = OtlpGrpcSpanExporter.builder().build()
        val tracerProvider = SdkTracerProvider
            .builder()
            .addSpanProcessor(SimpleSpanProcessor.create(spanExporter))
            .build()
        openTelemetry = OpenTelemetrySdk
            .builder()
            .setTracerProvider(tracerProvider)
            .buildAndRegisterGlobal()
    }

    fun getOpenTelemetry(): OpenTelemetry {
        require(::openTelemetry.isInitialized) { "TelemetryDI not initialized" }
        return openTelemetry
    }
}