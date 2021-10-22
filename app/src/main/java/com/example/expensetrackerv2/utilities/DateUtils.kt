package com.example.expensetrackerv2.utilities

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

object DateUtils {
    @JvmStatic
    fun dateToStringWithMonthAndYear(date: Date): String = SimpleDateFormat("MMMM yyyy").format(date)

    @JvmStatic
    fun toOnlyDateString(date: Date): String = SimpleDateFormat("dd/MM/yyyy").format(date)

    @JvmStatic
    fun stringToDate(stringDate: String): Date = SimpleDateFormat("dd/MM/yyyy").parse(stringDate)
}