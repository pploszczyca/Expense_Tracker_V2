package com.github.pploszczyca.expensetrackerv2.usecases.expense

import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class GetAllExpenses(
    private val repository: ExpenseRepository,
) {
    operator fun invoke(): Flow<List<Expense>> =
        repository.getAll()
}