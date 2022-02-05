package com.example.expensetrackerv2.database.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.expensetrackerv2.database.ExpenseDao
import com.example.expensetrackerv2.database.models.Expense
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.models.view_models.getKey

class ExpenseWithItsTypeDatabaseRepository(private val expenseDao: ExpenseDao) :
    ExpenseWithItsTypeRepository {

    private val expenseWithItsTypeLiveData = expenseDao.getAllExpenseWithItsType()

    override fun getExpenses(): LiveData<List<ExpenseWithItsType>> =
        expenseWithItsTypeLiveData

    override fun getExpenses(expenseMonthYearKey: ExpenseMonthYearKey?): LiveData<List<ExpenseWithItsType>> =
        Transformations.map(expenseWithItsTypeLiveData) { it ->
            it.filter { expenseMonthYearKey == null || it.getKey() == expenseMonthYearKey }
        }

    override fun getExpense(expenseID: Int): ExpenseWithItsType =
        expenseDao.getExpenseWithItsType(expenseID)

    private fun convertExpenseWithItsTypeToExpense(expenseWithItsType: ExpenseWithItsType): Expense =
        Expense(
            id = expenseWithItsType.id,
            title = expenseWithItsType.title,
            price = expenseWithItsType.price,
            date = expenseWithItsType.date,
            description = expenseWithItsType.description,
            place = expenseWithItsType.place,
            typeOfExpenseId = expenseWithItsType.typeID
        )

    override suspend fun insertExpense(expenseWithItsType: ExpenseWithItsType) =
        expenseDao.insertAllExpenses(
            convertExpenseWithItsTypeToExpense(expenseWithItsType)
        )

    override suspend fun deleteExpense(expenseWithItsType: ExpenseWithItsType) =
        expenseDao.deleteExpense(expenseWithItsType)

    override suspend fun updateExpense(expenseWithItsType: ExpenseWithItsType) =
        expenseDao.updateExpense(convertExpenseWithItsTypeToExpense(expenseWithItsType))
}