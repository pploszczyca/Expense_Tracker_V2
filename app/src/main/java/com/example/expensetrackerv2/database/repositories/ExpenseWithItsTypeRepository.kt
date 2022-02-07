package com.example.expensetrackerv2.database.repositories

import androidx.lifecycle.LiveData
import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType

interface ExpenseWithItsTypeRepository {
    fun getExpenses(): LiveData<List<ExpenseWithItsType>>
    fun getExpenses(
        expenseMonthYearKey: ExpenseMonthYearKey? = null,
        titleToSearch: String = ""
    ): LiveData<List<ExpenseWithItsType>>

    fun getExpense(expenseID: Int): ExpenseWithItsType

    suspend fun insertExpense(expenseWithItsType: ExpenseWithItsType)

    suspend fun deleteExpense(expenseWithItsType: ExpenseWithItsType)

    suspend fun updateExpense(expenseWithItsType: ExpenseWithItsType)
}
