package com.example.expensetrackerv2.utilities

import kotlin.math.round

object MathUtils {
    fun roundToMoney(value: Double): Double = round(value * 100) / 100
}