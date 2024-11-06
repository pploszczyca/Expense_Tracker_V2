package com.github.pploszczyca.expensetrackerv2.telemetry.spans

interface SpanContext {
    fun <T> inSpan(spanName: String, block: context(SpanContext) () -> T): T
}