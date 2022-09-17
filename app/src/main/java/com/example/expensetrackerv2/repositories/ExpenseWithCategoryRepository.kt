package com.example.expensetrackerv2.repositories

import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import kotlinx.coroutines.flow.Flow

interface ExpenseWithCategoryRepository {
    fun getExpenses(): Flow<List<ExpenseWithCategory>>

    fun getExpense(expenseID: Int): Flow<ExpenseWithCategory?>

    suspend fun insertExpense(expenseWithCategory: ExpenseWithCategory)

    suspend fun deleteExpense(expenseWithCategory: ExpenseWithCategory)

    suspend fun deleteAll()

    suspend fun updateExpense(expenseWithCategory: ExpenseWithCategory)
}
