package com.example.expensetrackerv2.use_cases.expense

import com.example.expensetrackerv2.database.ExpenseDao
import com.example.expensetrackerv2.models.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetExpenses @Inject constructor(private val expensesDao: ExpenseDao) {
    operator fun invoke(): Flow<List<ExpenseEntity>> = expensesDao.getAllExpenses()
}