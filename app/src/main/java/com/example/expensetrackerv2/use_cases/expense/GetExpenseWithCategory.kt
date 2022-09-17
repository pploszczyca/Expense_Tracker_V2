package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.database.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.database.repositories.ExpenseWithCategoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpenseWithCategory @Inject constructor(private val expenseWithCategoryRepository: ExpenseWithCategoryRepository) {
    operator fun invoke(expenseID: Int): Flow<ExpenseWithCategory?> =
        expenseWithCategoryRepository.getExpense(expenseID)
}