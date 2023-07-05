package com.github.pploszczyca.expensetrackerv2.features.main.extensions

import com.github.pploszczyca.expensetrackerv2.domain.Expense
import java.text.SimpleDateFormat
import java.util.Date

private const val MONTH_YEAR_DATE_STRING_PATTERN: String = "MMMM yyyy"

private val monthYearDateFormat = SimpleDateFormat(MONTH_YEAR_DATE_STRING_PATTERN)

fun Expense.MonthYearKey.toStringDate(): String = Date(
    this.year,
    this.month,
    1
).let(monthYearDateFormat::format)