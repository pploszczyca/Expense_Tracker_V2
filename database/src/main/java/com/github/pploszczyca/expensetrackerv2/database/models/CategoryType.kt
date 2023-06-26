package com.github.pploszczyca.expensetrackerv2.database.models

internal enum class CategoryType(val multiplier: Int) {
    INCOME(1),
    OUTGO(-1)
}