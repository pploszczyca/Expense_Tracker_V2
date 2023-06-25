package com.github.pploszczyca.expensetrackerv2.domain

data class Category(
    val id: Int,
    val name: String,
    val type: Type,
) {
    enum class Type(val multiplier: Int) {
        INCOME(1),
        OUTGO(-1)
    }
}
