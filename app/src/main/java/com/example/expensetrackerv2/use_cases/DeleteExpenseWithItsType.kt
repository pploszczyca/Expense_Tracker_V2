package com.example.expensetrackerv2.use_cases

import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeRepository
import javax.inject.Inject

class DeleteExpenseWithItsType @Inject constructor(private val expenseWithItsTypeRepository: ExpenseWithItsTypeRepository) {
    suspend operator fun invoke(expenseWithItsType: ExpenseWithItsType) =
        expenseWithItsTypeRepository.deleteExpense(expenseWithItsType)
}