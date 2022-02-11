package com.example.expensetrackerv2.database.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.expensetrackerv2.database.ExpenseDao
import com.example.expensetrackerv2.database.models.Expense
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.models.view_models.getKey
import com.example.expensetrackerv2.database.models.view_models.toExpense
import javax.inject.Inject

class ExpenseWithItsTypeDatabaseRepository @Inject constructor(private val expenseDao: ExpenseDao) :
    ExpenseWithItsTypeRepository {

    private val expenseWithItsTypeLiveData = expenseDao.getAllExpenseWithItsType()

    override fun getExpenses(): LiveData<List<ExpenseWithItsType>> =
        expenseWithItsTypeLiveData

    override fun getExpenses(
        expenseMonthYearKey: ExpenseMonthYearKey?,
        titleToSearch: String
    ): LiveData<List<ExpenseWithItsType>> =
        Transformations.map(expenseWithItsTypeLiveData) { it ->
            it.filter {
                (expenseMonthYearKey == null || it.getKey() == expenseMonthYearKey) && it.title.contains(
                    titleToSearch, true
                )
            }
        }

    override fun getExpense(expenseID: Int): LiveData<ExpenseWithItsType> =
        expenseDao.getExpenseWithItsType(expenseID)

    override suspend fun insertExpense(expenseWithItsType: ExpenseWithItsType) =
        expenseDao.insertAllExpenses(
            expenseWithItsType.toExpense()
        )

    override suspend fun deleteExpense(expenseWithItsType: ExpenseWithItsType) =
        expenseDao.deleteExpense(expenseWithItsType)

    override suspend fun updateExpense(expenseWithItsType: ExpenseWithItsType) =
        expenseDao.updateExpense(expenseWithItsType.toExpense())
}