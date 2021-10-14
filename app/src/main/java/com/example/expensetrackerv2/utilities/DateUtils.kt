package com.example.expensetrackerv2.utilities

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    @JvmStatic
    fun toOnlyDateString(date: Date): String = SimpleDateFormat("dd/MM/yyyy").format(date)
}