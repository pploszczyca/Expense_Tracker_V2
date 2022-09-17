package com.example.expensetrackerv2.database.repositories

import com.example.expensetrackerv2.database.ExpenseDao
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.database.models.view_models.toExpense
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseWithCategoryDatabaseRepository @Inject constructor(private val expenseDao: ExpenseDao) :
    ExpenseWithCategoryRepository {

    override fun getExpenses(): Flow<List<ExpenseWithCategory>> =
        expenseDao.getAllExpenseWithCategory()

    override fun getExpense(expenseID: Int): Flow<ExpenseWithCategory?> =
        expenseDao.getExpenseWithItsType(expenseID)

    override suspend fun insertExpense(expenseWithCategory: ExpenseWithCategory) =
        expenseDao.insertAllExpenses(
            expenseWithCategory.toExpense()
        )

    override suspend fun deleteExpense(expenseWithCategory: ExpenseWithCategory) =
        expenseDao.deleteExpense(expenseWithCategory)

    override suspend fun deleteAll() {
        expenseDao.deleteAllExpenses()
    }

    override suspend fun updateExpense(expenseWithCategory: ExpenseWithCategory) =
        expenseDao.updateExpense(expenseWithCategory.toExpense())
}