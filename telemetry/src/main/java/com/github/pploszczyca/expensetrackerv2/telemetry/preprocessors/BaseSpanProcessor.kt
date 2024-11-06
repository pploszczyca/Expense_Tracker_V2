package com.github.pploszczyca.expensetrackerv2.telemetry.preprocessors

import io.opentelemetry.context.Context
import io.opentelemetry.sdk.trace.ReadWriteSpan
import io.opentelemetry.sdk.trace.ReadableSpan
import io.opentelemetry.sdk.trace.SpanProcessor
import kotlin.math.max
import kotlin.time.Duration.Companion.nanoseconds

internal class BaseSpanProcessor(
    private val log: (String) -> Unit,
) : SpanProcessor {

    private var spansAmount = 0
    private val indent get () = "  ".repeat(max(spansAmount - 1, 0) )

    override fun onStart(parentContext: Context, span: ReadWriteSpan) {
        spansAmount++
        log("${indent}Span STARTED: ${span.name}, trace id: ${span.spanContext.traceId}")
    }

    override fun isStartRequired(): Boolean = true

    override fun onEnd(span: ReadableSpan) {
        val spanData = span.toSpanData()
        val duration = (spanData.endEpochNanos - spanData.startEpochNanos).nanoseconds
        log("${indent}Span ENDED: ${span.name} trace id: ${span.spanContext.traceId} [Duration: $duration]")
        spanData.attributes.forEach { attributeKey, value -> log("Attribute: $attributeKey -> $value") }
        spansAmount--
    }

    override fun isEndRequired(): Boolean = true
}