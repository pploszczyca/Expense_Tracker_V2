package com.github.pploszczyca.expensetrackerv2.usecases.expense

import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class GetExpensesPlaces(
    private val repository: ExpenseRepository,
) {
    suspend operator fun invoke(): List<String> =
        repository
            .getAll()
            .mapNotNull { it.place }
            .distinct()
}