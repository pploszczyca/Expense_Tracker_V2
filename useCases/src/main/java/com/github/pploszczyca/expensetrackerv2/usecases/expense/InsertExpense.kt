package com.github.pploszczyca.expensetrackerv2.usecases.expense

import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseDate
import com.github.pploszczyca.expensetrackerv2.domain.Price
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import java.util.Date

class InsertExpense(
    private val repository: ExpenseRepository,
) {
    suspend operator fun invoke(
        title: String,
        price: Price,
        date: ExpenseDate,
        description: String,
        place: String,
        type: Expense.Type,
        category: Category,
    ) {
        repository.insert(
            expense = Expense.new(
                title = title,
                price = price,
                date = date,
                description = description,
                place = place,
                type = type,
                category = category,
            )
        )
    }
}