package com.example.expensetrackerv2.use_cases.expense

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetExpensesTitles @Inject constructor(private val getExpensesWithItsType: GetExpensesWithItsType) {
    operator fun invoke(): Flow<List<String>> =
        getExpensesWithItsType().map { list -> list.map { it.title }.distinct() }
}