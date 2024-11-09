package com.github.pploszczyca.expensetrackerv2.telemetry.spans

import com.github.pploszczyca.expensetrackerv2.telemetry.di.TelemetryDI
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.SpanKind
import io.opentelemetry.context.Context

internal class DefaultSpanContext : SpanContext {
    private val tracer get() = TelemetryDI.getOpenTelemetry().getTracer("expense-tracker")

    override fun <T> inSpan(spanName: String, block: context(SpanContext) () -> T): T {
        val parentSpan = Span.current()
        val spanBuilder = tracer.spanBuilder(spanName)
            .setSpanKind(SpanKind.INTERNAL)

        if (parentSpan != Span.getInvalid()) {
            spanBuilder.setParent(Context.current().with(parentSpan))
        } else {
            spanBuilder.setNoParent()
        }

        val span = spanBuilder.startSpan()
        return Context.current().with(span).makeCurrent().use {
            block(this).also {
                span.end()
            }
        }
    }

    override suspend fun <T> inSpanSuspend(spanName: String, block: suspend context(SpanContext) () -> T): T {
        val parentSpan = Span.current()
        val spanBuilder = tracer.spanBuilder(spanName)
            .setSpanKind(SpanKind.INTERNAL)

        if (parentSpan != Span.getInvalid()) {
            spanBuilder.setParent(Context.current().with(parentSpan))
        } else {
            spanBuilder.setNoParent()
        }

        val span = spanBuilder.startSpan()
        return Context.current().with(span).makeCurrent().use {
            block(this).also {
                span.end()
            }
        }
    }
}