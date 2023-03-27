package com.example.expensetrackerv2.repositories

import com.example.expensetrackerv2.database.ExpenseDao
import com.example.expensetrackerv2.models.ExpenseEntity
import javax.inject.Inject

class ExpenseDatabaseRepository @Inject constructor(private val dao: ExpenseDao) : ExpenseRepository {
    override suspend fun insert(expense: ExpenseEntity) {
        dao.insertAllExpenses(expense)
    }

    override suspend fun update(expense: ExpenseEntity) {
        dao.updateExpense(expense)
    }
}