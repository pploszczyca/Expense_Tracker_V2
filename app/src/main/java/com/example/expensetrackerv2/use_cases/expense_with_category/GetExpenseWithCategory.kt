package com.example.expensetrackerv2.use_cases.expense_with_category

import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.repositories.ExpenseWithCategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpenseWithCategory @Inject constructor(
    private val repository: ExpenseWithCategoryRepository,
) {
    operator fun invoke(expenseID: Int): Flow<ExpenseWithCategory?> =
        repository.get(expenseID)
}