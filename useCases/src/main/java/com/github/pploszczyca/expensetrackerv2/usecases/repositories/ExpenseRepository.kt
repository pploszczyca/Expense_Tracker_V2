package com.github.pploszczyca.expensetrackerv2.usecases.repositories

import com.github.pploszczyca.expensetrackerv2.domain.Expense
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun getAll(): Flow<List<Expense>>

    fun get(expenseId: Int): Flow<Expense>

    suspend fun insert(expense: Expense)

    suspend fun update(expense: Expense)

    suspend fun delete(expense: Expense)
}