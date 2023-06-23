package com.example.expensetrackerv2.use_cases.expense_with_category

import com.example.expensetrackerv2.repositories.ExpenseWithCategoryRepository
import javax.inject.Inject

class DeleteAllExpensesWithCategory @Inject constructor(
    private val repository: ExpenseWithCategoryRepository,
) {
    suspend operator fun invoke() {
        repository.deleteAll()
    }
}