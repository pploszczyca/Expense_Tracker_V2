package com.github.pploszczyca.expensetrackerv2.telemetry.di

import com.github.pploszczyca.expensetrackerv2.telemetry.preprocessors.BaseSpanProcessor
import io.opentelemetry.api.OpenTelemetry
import io.opentelemetry.sdk.OpenTelemetrySdk
import io.opentelemetry.sdk.trace.SdkTracerProvider

object TelemetryDI {
    private lateinit var openTelemetry: OpenTelemetry

    fun init(
        log: (String) -> Unit,
    ) {
        val tracerProvider = SdkTracerProvider
            .builder()
            .addSpanProcessor(BaseSpanProcessor(log))
            .build()
        openTelemetry = OpenTelemetrySdk
            .builder()
            .setTracerProvider(tracerProvider)
            .buildAndRegisterGlobal()
    }

    internal fun getOpenTelemetry(): OpenTelemetry {
        require(TelemetryDI::openTelemetry.isInitialized) { "TelemetryDI not initialized" }
        return openTelemetry
    }
}