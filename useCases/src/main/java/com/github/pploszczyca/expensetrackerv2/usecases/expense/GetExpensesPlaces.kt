package com.github.pploszczyca.expensetrackerv2.usecases.expense

import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository

class GetExpensesPlaces(
    private val repository: ExpenseRepository,
) {
    suspend operator fun invoke(): List<String> =
        repository
            .getAll()
            .mapNotNull { it.place }
            .distinct()
}