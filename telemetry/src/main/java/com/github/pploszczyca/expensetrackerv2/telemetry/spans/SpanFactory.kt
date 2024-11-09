package com.github.pploszczyca.expensetrackerv2.telemetry.spans

interface SpanFactory {
    fun create(): SpanContext
}

class DefaultSpanFactory : SpanFactory {
    override fun create(): SpanContext = DefaultSpanContext()
}