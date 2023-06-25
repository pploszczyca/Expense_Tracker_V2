package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.repositories.ExpenseRepository
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import javax.inject.Inject

class DeleteExpense @Inject constructor(
    private val repository: ExpenseRepository,
) {
    suspend operator fun invoke(expense: Expense) {
        repository.delete(expense = expense)
    }
}