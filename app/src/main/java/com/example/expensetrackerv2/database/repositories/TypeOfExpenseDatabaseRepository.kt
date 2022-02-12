package com.example.expensetrackerv2.database.repositories

import com.example.expensetrackerv2.database.ExpenseDao
import com.example.expensetrackerv2.database.models.TypeOfExpense
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TypeOfExpenseDatabaseRepository @Inject constructor(private val expenseDao: ExpenseDao) :
    TypeOfExpenseRepository {
    private val typeOfExpenseLiveData = expenseDao.getAllTypesOfExpense()

    override fun getAllTypeOfExpenses(): Flow<List<TypeOfExpense>> = typeOfExpenseLiveData

    override suspend fun insertTypeOfExpense(typeOfExpense: TypeOfExpense) =
        expenseDao.insertAllTypesOfExpense(typeOfExpense)

    override suspend fun deleteTypeOfExpense(typeOfExpense: TypeOfExpense) =
        expenseDao.deleteTypeOfExpense(typeOfExpense)

    override suspend fun updateTypeOfExpense(typeOfExpense: TypeOfExpense) =
        expenseDao.updateTypeOfExpense(typeOfExpense)
}