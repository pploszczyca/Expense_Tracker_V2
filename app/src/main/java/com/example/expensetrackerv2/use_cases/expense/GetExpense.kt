package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.models.ExpenseEntity
import com.example.expensetrackerv2.repositories.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpense @Inject constructor(private val repository: ExpenseRepository){
    operator fun invoke(expenseId: Int): Flow<ExpenseEntity> =
        repository.get(expenseId)
}