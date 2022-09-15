package com.example.expensetrackerv2.utilities

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    private const val DATE_STRING_PATTERN: String = "yyyy-MM-dd"

    private val simpleDateFormat = SimpleDateFormat(DATE_STRING_PATTERN)

    @JvmStatic
    fun toOnlyDateString(date: Date): String = simpleDateFormat.format(date)

    @JvmStatic
    fun stringToDate(stringDate: String): Date = simpleDateFormat.parse(stringDate)
}