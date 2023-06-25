package com.github.pploszczyca.expensetrackerv2.domain

import java.util.Date

data class Expense(
    val id: Int,
    val title: String,
    val price: Double,
    val date: Date,
    val description: String,
    val place: String,
    val category: Category,
)
