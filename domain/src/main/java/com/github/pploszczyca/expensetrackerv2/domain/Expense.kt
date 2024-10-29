package com.github.pploszczyca.expensetrackerv2.domain

data class Expense(
    val id: Id,
    val title: String,
    val price: Price,
    val date: ExpenseDate,
    val description: String?,
    val place: String?,
    val type: Type,
    val category: Category?,
) {
    sealed interface Type {
        data object Income : Type
        data object Outgo : Type
    }

    val signedAmount: Price
        get() = when (type) {
            is Type.Income -> price
            is Type.Outgo -> price * Price.MINUS_ONE
        }

    companion object {
        fun new(
            title: String,
            price: Price,
            date: ExpenseDate,
            description: String?,
            place: String?,
            type: Type,
            category: Category?,
        ): Expense = Expense(
            id = Id.new(),
            title = title,
            price = price,
            date = date,
            description = description,
            place = place,
            type = type,
            category = category,
        )
    }
}

val List<Expense>.total get(): Price = this
    .sumOf { it.signedAmount }

val List<Expense>.totalIncome get(): Price = this
    .filter { it.type == Expense.Type.Income }
    .sumOf { it.signedAmount }

val List<Expense>.totalOutgo get(): Price = this
    .filter { it.type == Expense.Type.Outgo }
    .sumOf { it.signedAmount }
