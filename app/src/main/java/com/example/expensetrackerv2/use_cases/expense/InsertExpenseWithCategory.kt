package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.repositories.ExpenseWithCategoryRepository
import javax.inject.Inject

class InsertExpenseWithCategory @Inject constructor(
    private val repository: ExpenseWithCategoryRepository,
) {
    suspend operator fun invoke(expenseWithCategory: ExpenseWithCategory) =
        repository.insertExpense(expenseWithCategory)
}