package com.github.pploszczyca.expensetrackerv2.usecases.expense

import com.github.pploszczyca.expensetrackerv2.telemetry.spans.SpanContext
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository

class GetExpensesTitles(
    private val repository: ExpenseRepository,
) {
    context(SpanContext)
    suspend operator fun invoke(): List<String> =
        inSpanSuspend("GetExpensesTitles") {
            repository
                .getAll()
                .map { it.title }
                .distinct()
        }
}