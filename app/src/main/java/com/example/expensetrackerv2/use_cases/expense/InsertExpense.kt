package com.example.expensetrackerv2.use_cases.expense

import com.github.pploszczyca.expensetrackerv2.usecases.repositories.ExpenseRepository
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import java.util.Date
import javax.inject.Inject

class InsertExpense @Inject constructor(
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