package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.repositories.ExpenseWithCategoryRepository
import javax.inject.Inject

class UpdateExpenseWithCategory @Inject constructor(private val expenseWithCategoryRepository: ExpenseWithCategoryRepository) {
    suspend operator fun invoke(expenseWithCategory: ExpenseWithCategory) =
        expenseWithCategoryRepository.updateExpense(expenseWithCategory)
}