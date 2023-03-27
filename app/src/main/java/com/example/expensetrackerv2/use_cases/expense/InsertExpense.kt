package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.models.ExpenseEntity
import com.example.expensetrackerv2.repositories.ExpenseRepository
import java.util.*
import javax.inject.Inject

class InsertExpense @Inject constructor(
    private val expenseRepository: ExpenseRepository,
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
        expenseRepository.insertExpense(
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