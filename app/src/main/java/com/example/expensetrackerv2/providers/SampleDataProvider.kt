package com.example.expensetrackerv2.providers

import android.content.Context
import com.example.expensetrackerv2.database.AppDatabase
import com.example.expensetrackerv2.database.models.Expense
import com.example.expensetrackerv2.database.models.Type
import com.example.expensetrackerv2.database.models.TypeOfExpense
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object SampleDataProvider {
    fun sampleTypeOfExpense(context: Context?) {
        runBlocking {
            launch {
                AppDatabase.getInstance(context).expenseDao().insertAllTypesOfExpense(
                    TypeOfExpense(name = "Expense", type = Type.OUTGO),
                    TypeOfExpense(name = "Income", type = Type.INCOME)
                )
            }
        }
    }

    fun sampleExpenses(context: Context?) {
        runBlocking {
            launch {
                AppDatabase.getInstance(context).expenseDao().insertAllExpenses(
                    Expense(
                        title = "Zakupy w Biedronce",
                        price = 50.0,
                        description = "Opis",
                        place = "Wadowice",
                        typeOfExpenseId = 1
                    ),
                    Expense(
                        title = "Zakupy w Lidlu",
                        price = 30.0,
                        description = "Opis",
                        place = "Wadowice",
                        typeOfExpenseId = 1
                    ),
                    Expense(
                        title = "Kabel HDMI",
                        price = 100.0,
                        description = "Opis",
                        place = "Kraków",
                        typeOfExpenseId = 1
                    ),
                    Expense(
                        title = "Na życie",
                        price = 1000.0,
                        description = "Opis",
                        place = "",
                        typeOfExpenseId = 2
                    )
                )
            }
        }
    }
}