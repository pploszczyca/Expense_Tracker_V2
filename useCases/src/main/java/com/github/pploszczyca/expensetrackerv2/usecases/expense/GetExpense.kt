package com.github.pploszczyca.expensetrackerv2.usecases.expense

import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.Id
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetExpense(
    private val repository: ExpenseRepository,
) {
    suspend operator fun invoke(expenseId: Id): Expense =
        repository.get(expenseId)
}