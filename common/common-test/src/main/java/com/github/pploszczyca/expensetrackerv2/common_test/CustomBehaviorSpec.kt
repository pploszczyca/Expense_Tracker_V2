package com.github.pploszczyca.expensetrackerv2.common_test

import com.github.pploszczyca.expensetrackerv2.telemetry.spans.SpanContext
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.BehaviorSpec

abstract class CustomBehaviorSpec(
    body: context(SpanContext, BehaviorSpec) () -> Unit = {}
) : BehaviorSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    body(NoOpSpanContext(), this)
})

private class NoOpSpanContext : SpanContext {
    override fun <T> inSpan(spanName: String, block: SpanContext.() -> T): T = block()
    override suspend fun <T> inSpanSuspend(spanName: String, block: suspend SpanContext.() -> T): T = block()
}