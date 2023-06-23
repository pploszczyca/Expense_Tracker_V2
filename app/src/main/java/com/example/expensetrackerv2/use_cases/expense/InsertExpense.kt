package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.models.ExpenseEntity
import com.example.expensetrackerv2.repositories.ExpenseRepository
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
        categoryId: Int,
    ) {
        repository.insert(
            expense = ExpenseEntity(
                title = title,
                price = price,
                date = date,
                description = description,
                place = place,
                categoryId = categoryId,
            )
        )
    }
}