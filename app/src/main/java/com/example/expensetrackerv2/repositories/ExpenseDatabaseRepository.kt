package com.example.expensetrackerv2.repositories

import com.example.expensetrackerv2.database.ExpenseDao
import com.example.expensetrackerv2.models.ExpenseEntity
import javax.inject.Inject

class ExpenseDatabaseRepository @Inject constructor(private val dao: ExpenseDao) : ExpenseRepository {
    override suspend fun insertExpense(expense: ExpenseEntity) {
        dao.insertAllExpenses(expense)
    }
}