package com.github.pploszczyca.expensetrackerv2.usecases.expense

import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import kotlinx.coroutines.flow.Flow

class GetAllExpenses(
    private val repository: ExpenseRepository,
) {
    operator fun invoke(): Flow<List<Expense>> =
        repository.getAll()
}