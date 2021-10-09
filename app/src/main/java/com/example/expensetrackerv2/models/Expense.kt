package com.example.expensetrackerv2.models

import java.util.*

data class Expense(
    val id: Int = 0,
    var title: String = "",
    var price: Double = 0.0,
    var date: Date = Date(),
    var description: String = "",
    var place: String = ""
)
