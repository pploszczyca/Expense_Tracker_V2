package com.github.pploszczyca.expensetrackerv2.domain

data class Category(
    val id: Id,
    val name: String,
) {
    companion object {
        fun new(name: String): Category = Category(
            id = Id.new(),
            name = name,
        )
    }
}
