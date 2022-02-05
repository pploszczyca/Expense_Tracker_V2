package com.example.expensetrackerv2.utilities

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    @JvmStatic
    fun dateToStringWithMonthAndYear(date: Date): String =
        SimpleDateFormat("MMMM yyyy").format(date)

    @JvmStatic
    fun toOnlyDateString(date: Date): String = SimpleDateFormat("yyyy-MM-dd").format(date)

    @JvmStatic
    fun stringToDate(stringDate: String): Date = SimpleDateFormat("yyyy-MM-dd").parse(stringDate)
}