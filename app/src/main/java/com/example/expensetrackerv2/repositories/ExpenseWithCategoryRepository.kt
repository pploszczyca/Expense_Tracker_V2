package com.example.expensetrackerv2.repositories

import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import kotlinx.coroutines.flow.Flow

interface ExpenseWithCategoryRepository {
    fun getAll(): Flow<List<ExpenseWithCategory>>

    fun get(expenseID: Int): Flow<ExpenseWithCategory?>

    suspend fun insert(expenseWithCategory: ExpenseWithCategory)

    suspend fun delete(expenseWithCategory: ExpenseWithCategory)

    suspend fun deleteAll()

    suspend fun update(expenseWithCategory: ExpenseWithCategory)
}
