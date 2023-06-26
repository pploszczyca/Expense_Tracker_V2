package com.github.pploszczyca.expensetrackerv2.usecases.expense

import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import java.util.Date

class InsertExpense(
    private val repository: ExpenseRepository,
) {
    suspend operator fun invoke(
        title: String,
        price: Double,
        date: Date,
        description: String,
        place: String,
        category: Category,
    ) {
        repository.insert(
            expense = Expense(
                title = title,
                price = price,
                date = date,
                description = description,
                place = place,
                category = category,
            )
        )
    }
}