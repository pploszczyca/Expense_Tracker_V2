package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.database.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.database.repositories.ExpenseWithCategoryRepository
import javax.inject.Inject

class InsertExpenseWithCategory @Inject constructor(private val expenseWithCategoryRepository: ExpenseWithCategoryRepository) {
    suspend operator fun invoke(expenseWithCategory: ExpenseWithCategory) =
        expenseWithCategoryRepository.insertExpense(expenseWithCategory)
}