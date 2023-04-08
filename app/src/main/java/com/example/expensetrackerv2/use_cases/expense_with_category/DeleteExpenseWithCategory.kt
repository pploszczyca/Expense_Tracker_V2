package com.example.expensetrackerv2.use_cases.expense_with_category

import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.repositories.ExpenseWithCategoryRepository
import javax.inject.Inject

class DeleteExpenseWithCategory @Inject constructor(
    private val repository: ExpenseWithCategoryRepository,
) {
    suspend operator fun invoke(expenseWithCategory: ExpenseWithCategory) {
        repository.deleteExpense(expenseWithCategory)
    }
}