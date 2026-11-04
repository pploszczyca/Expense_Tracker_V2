package com.github.pploszczyca.expensetrackerv2.telemetry.spans

interface SpanContext {
    fun <T> inSpan(spanName: String, block: context(SpanContext) () -> T): T

    suspend fun <T> inSpanSuspend(spanName: String, block: suspend context(SpanContext) () -> T): T
}

context(spanContext: SpanContext)
fun <T> inSpan(spanName: String, block: context(SpanContext) () -> T): T =
    spanContext.inSpan(spanName, block)

context(spanContext: SpanContext)
suspend fun <T> inSpanSuspend(spanName: String, block: suspend context(SpanContext) () -> T): T =
    spanContext.inSpanSuspend(spanName, block)