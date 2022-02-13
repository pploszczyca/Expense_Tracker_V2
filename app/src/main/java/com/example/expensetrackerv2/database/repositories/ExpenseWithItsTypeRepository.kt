package com.example.expensetrackerv2.database.repositories

import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import kotlinx.coroutines.flow.Flow

interface ExpenseWithItsTypeRepository {
    fun getExpenses(): Flow<List<ExpenseWithItsType>>

    fun getExpense(expenseID: Int): Flow<ExpenseWithItsType?>

    suspend fun insertExpense(expenseWithItsType: ExpenseWithItsType)

    suspend fun deleteExpense(expenseWithItsType: ExpenseWithItsType)

    suspend fun deleteAll()

    suspend fun updateExpense(expenseWithItsType: ExpenseWithItsType)
}
