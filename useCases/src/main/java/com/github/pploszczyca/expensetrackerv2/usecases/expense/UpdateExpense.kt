package com.github.pploszczyca.expensetrackerv2.usecases.expense

import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseDate
import com.github.pploszczyca.expensetrackerv2.domain.Id
import com.github.pploszczyca.expensetrackerv2.domain.Price
import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import java.util.Date

class UpdateExpense(
    private val repository: ExpenseRepository,
) {
    suspend operator fun invoke(
        id: Id,
        title: String,
        price: Price,
        date: ExpenseDate,
        description: String?,
        place: String?,
        type: Expense.Type,
        category: Category?,
    ) {
        repository.update(
            expense = Expense(
                id = id,
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