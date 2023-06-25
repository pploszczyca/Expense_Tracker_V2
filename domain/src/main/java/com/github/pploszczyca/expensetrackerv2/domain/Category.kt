package com.github.pploszczyca.expensetrackerv2.domain

data class Category(
    val id: Int = NEW_CATEGORY_ID,
    val name: String,
    val type: Type,
) {
    enum class Type(val multiplier: Int) {
        INCOME(1),
        OUTGO(-1)
    }

    private companion object {
        const val NEW_CATEGORY_ID = 0
    }
}
