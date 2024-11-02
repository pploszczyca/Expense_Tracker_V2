package com.github.pploszczyca.expensetrackerv2.usecases.repositories

import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.Id
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository {
    fun observe(): Flow<List<Expense>>

    suspend fun getAll(): List<Expense>

    suspend fun get(expenseId: Id): Expense

    suspend fun insert(expense: Expense)

    suspend fun update(expense: Expense)

    suspend fun delete(expense: Expense)
}