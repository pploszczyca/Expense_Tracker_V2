package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeRepository
import javax.inject.Inject

class InsertExpenseWithItsType @Inject constructor(private val expenseWithItsTypeRepository: ExpenseWithItsTypeRepository) {
    suspend operator fun invoke(expenseWithItsType: ExpenseWithItsType) =
        expenseWithItsTypeRepository.insertExpense(expenseWithItsType)
}