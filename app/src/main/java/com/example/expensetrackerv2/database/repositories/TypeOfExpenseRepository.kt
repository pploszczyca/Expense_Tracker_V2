package com.example.expensetrackerv2.database.repositories

import com.example.expensetrackerv2.database.models.TypeOfExpense
import kotlinx.coroutines.flow.Flow

interface TypeOfExpenseRepository {
    fun getAllTypeOfExpenses(): Flow<List<TypeOfExpense>>

    suspend fun insertTypeOfExpense(typeOfExpense: TypeOfExpense)

    suspend fun deleteTypeOfExpense(typeOfExpense: TypeOfExpense)

    suspend fun updateTypeOfExpense(typeOfExpense: TypeOfExpense)
}