package com.github.pploszczyca.expensetrackerv2.telemetry.spans

import com.github.pploszczyca.expensetrackerv2.telemetry.di.TelemetryDI

class DefaultSpanContext : SpanContext {

    private val tracer get() = TelemetryDI.getOpenTelemetry().getTracer("expense-tracker")
    override fun <T> inSpan(spanName: String, block: context(SpanContext) () -> T): T {
        val span = tracer.spanBuilder(spanName).startSpan()

        return block(this).also {
            span.end()
        }
    }
}