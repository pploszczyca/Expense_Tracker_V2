package com.example.expensetrackerv2.use_cases.expense_with_category

import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.repositories.ExpenseWithCategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllExpenseWithCategory @Inject constructor(
    private val repository: ExpenseWithCategoryRepository,
) {
    operator fun invoke(): Flow<List<ExpenseWithCategory>> =
        repository.getExpenses()
}