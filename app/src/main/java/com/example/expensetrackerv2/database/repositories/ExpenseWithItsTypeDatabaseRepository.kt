package com.example.expensetrackerv2.database.repositories

import com.example.expensetrackerv2.database.ExpenseDao
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.models.view_models.toExpense
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExpenseWithItsTypeDatabaseRepository @Inject constructor(private val expenseDao: ExpenseDao) :
    ExpenseWithItsTypeRepository {

    override fun getExpenses(): Flow<List<ExpenseWithItsType>> =
        expenseDao.getAllExpenseWithItsType()

    override fun getExpense(expenseID: Int): Flow<ExpenseWithItsType?> =
        expenseDao.getExpenseWithItsType(expenseID)

    override suspend fun insertExpense(expenseWithItsType: ExpenseWithItsType) =
        expenseDao.insertAllExpenses(
            expenseWithItsType.toExpense()
        )

    override suspend fun deleteExpense(expenseWithItsType: ExpenseWithItsType) =
        expenseDao.deleteExpense(expenseWithItsType)

    override suspend fun deleteAll() {
        expenseDao.deleteAllExpenses()
    }

    override suspend fun updateExpense(expenseWithItsType: ExpenseWithItsType) =
        expenseDao.updateExpense(expenseWithItsType.toExpense())
}