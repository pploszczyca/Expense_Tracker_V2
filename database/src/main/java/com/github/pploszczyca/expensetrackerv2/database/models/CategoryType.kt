package com.github.pploszczyca.expensetrackerv2.database.models

enum class CategoryType(val multiplier: Int) {
    INCOME(1),
    OUTGO(-1)
}