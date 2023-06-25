package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.repositories.ExpenseRepository
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpense @Inject constructor(
    private val repository: ExpenseRepository,
) {
    operator fun invoke(expenseId: Int): Flow<Expense> =
        repository.get(expenseId)
}