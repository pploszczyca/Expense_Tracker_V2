package com.example.expensetrackerv2.utilities

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private const val DATE_STRING_PATTERN: String = "yyyy-MM-dd"
    private const val MONTH_YEAR_DATE_STRING_PATTERN: String = "MMMM yyyy"

    @JvmStatic
    fun dateToStringWithMonthAndYear(date: Date): String =
        SimpleDateFormat(MONTH_YEAR_DATE_STRING_PATTERN).format(date)

    @JvmStatic
    fun toOnlyDateString(date: Date): String = SimpleDateFormat(DATE_STRING_PATTERN).format(date)

    @JvmStatic
    fun stringToDate(stringDate: String): Date =
        SimpleDateFormat(DATE_STRING_PATTERN).parse(stringDate)
}