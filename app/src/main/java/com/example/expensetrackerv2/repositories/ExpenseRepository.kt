package com.example.expensetrackerv2.repositories

import com.example.expensetrackerv2.models.ExpenseEntity
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getAll(): Flow<List<ExpenseEntity>>

    fun get(expenseId: Int): Flow<ExpenseEntity>

    suspend fun insert(expense: ExpenseEntity)

    suspend fun update(expense: ExpenseEntity)
}