package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.repositories.ExpenseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetExpensesPlaces @Inject constructor(
    private val repository: ExpenseRepository,
) {
    operator fun invoke(): Flow<List<String>> =
        repository
            .getAll()
            .map { list -> list.map { it.place }.distinct() }
            .distinctUntilChanged()
}