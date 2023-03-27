package com.example.expensetrackerv2.repositories

import com.example.expensetrackerv2.models.ExpenseEntity

interface ExpenseRepository {
    suspend fun insertExpense(expense: ExpenseEntity)
}