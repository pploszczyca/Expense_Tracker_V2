package com.github.pploszczyca.expensetrackerv2.usecases.expense

import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

class GetExpensesTitles(
    private val repository: ExpenseRepository,
) {
    suspend operator fun invoke(): List<String> =
        repository
        .getAll()
        .map { it.title }
        .distinct()
}