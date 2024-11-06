package com.github.pploszczyca.expensetrackerv2.telemetry.spans

interface SpanContext {
    fun <T> inSpan(spanName: String, block: context(SpanContext) () -> T): T

    suspend fun <T> inSpanSuspend(spanName: String, block: suspend context(SpanContext) () -> T): T
}