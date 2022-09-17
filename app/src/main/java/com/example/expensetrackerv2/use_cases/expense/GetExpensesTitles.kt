package com.example.expensetrackerv2.use_cases.expense

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetExpensesTitles @Inject constructor(private val getAllExpenseWithCategory: GetAllExpenseWithCategory) {
    operator fun invoke(): Flow<List<String>> =
        getAllExpenseWithCategory()
            .map { list -> list.map { it.title }.distinct() }
            .distinctUntilChanged()
}