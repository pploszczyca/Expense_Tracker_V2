package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.repositories.ExpenseWithCategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetExpenseWithCategory @Inject constructor(private val expenseWithCategoryRepository: ExpenseWithCategoryRepository) {
    operator fun invoke(expenseID: Int): Flow<ExpenseWithCategory?> =
        expenseWithCategoryRepository.getExpense(expenseID)
}