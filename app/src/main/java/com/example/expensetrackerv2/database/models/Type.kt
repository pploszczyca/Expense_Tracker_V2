package com.example.expensetrackerv2.database.models

enum class Type(val multiplier: Int) {
    INCOME(1),
    OUTGO(-1)
}