package com.example.expensetrackerv2.database.repositories

import androidx.lifecycle.LiveData
import com.example.expensetrackerv2.database.ExpenseDao
import com.example.expensetrackerv2.database.models.TypeOfExpense

class TypeOfExpenseDatabaseRepository(private val expenseDao: ExpenseDao) :
    TypeOfExpenseRepository {
    private val typeOfExpenseLiveData = expenseDao.getAllTypesOfExpense()

    override fun getAllTypeOfExpenses(): LiveData<List<TypeOfExpense>> = typeOfExpenseLiveData

    override suspend fun insertTypeOfExpense(typeOfExpense: TypeOfExpense) =
        expenseDao.insertAllTypesOfExpense(typeOfExpense)

    override suspend fun deleteTypeOfExpense(typeOfExpense: TypeOfExpense) =
        expenseDao.deleteTypeOfExpense(typeOfExpense)

    override suspend fun updateTypeOfExpense(typeOfExpense: TypeOfExpense) =
        expenseDao.updateTypeOfExpense(typeOfExpense)
}