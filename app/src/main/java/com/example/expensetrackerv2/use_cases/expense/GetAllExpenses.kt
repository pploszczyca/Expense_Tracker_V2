package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.models.ExpenseEntity
import com.example.expensetrackerv2.repositories.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllExpenses @Inject constructor(
    private val repository: ExpenseRepository,
) {
    operator fun invoke(): Flow<List<ExpenseEntity>> =
        repository.getAll()
}