package com.example.expensetrackerv2.use_cases

import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpenseWithItsType @Inject constructor(private val expenseWithItsTypeRepository: ExpenseWithItsTypeRepository) {
    operator fun invoke(expenseID: Int): Flow<ExpenseWithItsType?> =
        expenseWithItsTypeRepository.getExpense(expenseID)
}