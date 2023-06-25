package com.example.expensetrackerv2.extensions

import com.example.expensetrackerv2.models.view_models.ExpenseMonthYearKey
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import java.text.SimpleDateFormat
import java.util.Date

private const val MONTH_YEAR_DATE_STRING_PATTERN: String = "MMMM yyyy"

private val monthYearDateFormat = SimpleDateFormat(MONTH_YEAR_DATE_STRING_PATTERN)

fun ExpenseMonthYearKey.toStringDate(): String = Date(
    this.year,
    this.month,
    1
).let(monthYearDateFormat::format)

fun Expense.MonthYearKey.toStringDate(): String = Date(
    this.year,
    this.month,
    1
).let(monthYearDateFormat::format)
