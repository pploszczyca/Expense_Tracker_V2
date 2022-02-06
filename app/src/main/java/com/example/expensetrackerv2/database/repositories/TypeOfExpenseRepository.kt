package com.example.expensetrackerv2.database.repositories

import androidx.lifecycle.LiveData
import com.example.expensetrackerv2.database.models.TypeOfExpense

interface TypeOfExpenseRepository {
    fun getAllTypeOfExpenses(): LiveData<List<TypeOfExpense>>

    suspend fun insertTypeOfExpense(typeOfExpense: TypeOfExpense)

    suspend fun deleteTypeOfExpense(typeOfExpense: TypeOfExpense)

    suspend fun updateTypeOfExpense(typeOfExpense: TypeOfExpense)
}