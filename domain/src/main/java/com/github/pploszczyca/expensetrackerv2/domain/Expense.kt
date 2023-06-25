package com.github.pploszczyca.expensetrackerv2.domain

import java.util.Date

data class Expense(
    val id: Int = NEW_EXPENSE_ID,
    val title: String,
    val price: Double,
    val date: Date,
    val description: String,
    val place: String,
    val category: Category,
) {
    val monthYearKey: MonthYearKey
        get() = MonthYearKey(date.year, date.month)

    data class MonthYearKey(val year: Int, val month: Int)

    private companion object {
        const val NEW_EXPENSE_ID = 0
    }
}
