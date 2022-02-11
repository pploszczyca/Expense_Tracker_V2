package com.example.expensetrackerv2.use_cases

import androidx.lifecycle.LiveData
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeRepository
import javax.inject.Inject

class GetExpenseWithItsType @Inject constructor(private val expenseWithItsTypeRepository: ExpenseWithItsTypeRepository) {
    operator fun invoke(expenseID: Int): LiveData<ExpenseWithItsType> = expenseWithItsTypeRepository.getExpense(expenseID)
}