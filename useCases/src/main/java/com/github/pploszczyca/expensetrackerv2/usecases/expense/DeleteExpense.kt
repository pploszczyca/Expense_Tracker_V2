package com.github.pploszczyca.expensetrackerv2.usecases.expense

import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository

class DeleteExpense(
    private val repository: ExpenseRepository,
) {
    suspend operator fun invoke(expense: Expense) {
        repository.delete(expense = expense)
    }
}