package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpensesWithItsType @Inject constructor(private val expenseWithItsTypeRepository: ExpenseWithItsTypeRepository) {
    operator fun invoke(): Flow<List<ExpenseWithItsType>> =
        expenseWithItsTypeRepository.getExpenses()
}