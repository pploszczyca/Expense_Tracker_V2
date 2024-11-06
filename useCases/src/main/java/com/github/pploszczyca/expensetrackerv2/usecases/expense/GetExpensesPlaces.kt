package com.github.pploszczyca.expensetrackerv2.usecases.expense

import com.github.pploszczyca.expensetrackerv2.telemetry.spans.SpanContext
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository

class GetExpensesPlaces(
    private val repository: ExpenseRepository,
) {
    context(SpanContext)
    suspend operator fun invoke(): List<String> =
        inSpanSuspend("GetExpensesPlaces") {
            repository
                .getAll()
                .mapNotNull { it.place }
                .distinct()
        }
}