package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.models.ExpenseEntity
import com.example.expensetrackerv2.repositories.ExpenseRepository
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
        categoryId: Int,
    ) {
        repository.update(
            expense = ExpenseEntity(
                id = id,
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