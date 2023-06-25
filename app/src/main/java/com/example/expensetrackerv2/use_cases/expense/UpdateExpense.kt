package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.repositories.ExpenseRepository
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import java.util.Date
import javax.inject.Inject

class UpdateExpense @Inject constructor(
    private val repository: ExpenseRepository,
) {
    suspend operator fun invoke(
        id: Int,
        title: String,
        price: Double,
        date: Date,
        description: String,
        place: String,
        category: Category,
    ) {
        repository.update(
            expense = Expense(
                id = id,
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