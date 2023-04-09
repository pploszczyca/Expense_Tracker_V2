package com.example.expensetrackerv2.repositories

import com.example.expensetrackerv2.database.ExpenseDao
import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.models.view_models.toExpense
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseWithCategoryDatabaseRepository @Inject constructor(private val expenseDao: ExpenseDao) :
    ExpenseWithCategoryRepository {

    override fun getAll(): Flow<List<ExpenseWithCategory>> =
        expenseDao.getAllExpenseWithCategory()

    override fun get(expenseID: Int): Flow<ExpenseWithCategory?> =
        expenseDao.getExpenseWithItsType(expenseID)

    override suspend fun insert(expenseWithCategory: ExpenseWithCategory) =
        expenseDao.insertAllExpenses(
            expenseWithCategory.toExpense()
        )

    override suspend fun delete(expenseWithCategory: ExpenseWithCategory) =
        expenseDao.deleteExpense(expenseWithCategory)

    override suspend fun deleteAll() {
        expenseDao.deleteAllExpenses()
    }

    override suspend fun update(expenseWithCategory: ExpenseWithCategory) =
        expenseDao.updateExpense(expenseWithCategory.toExpense())
}