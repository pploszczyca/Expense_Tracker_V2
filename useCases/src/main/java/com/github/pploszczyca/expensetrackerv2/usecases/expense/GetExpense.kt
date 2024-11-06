package com.github.pploszczyca.expensetrackerv2.usecases.expense

import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.Id
import com.github.pploszczyca.expensetrackerv2.telemetry.spans.SpanContext
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository

class GetExpense(
    private val repository: ExpenseRepository,
) {
    context(SpanContext)
    suspend operator fun invoke(expenseId: Id): Expense =
        inSpanSuspend("GetExpense") {
            repository.get(expenseId)
        }
}