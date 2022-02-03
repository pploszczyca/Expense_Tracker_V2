package com.example.expensetrackerv2.utilities

import com.example.expensetrackerv2.database.models.Expense
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JSONUtils {
    private val gson = Gson()

    fun exportExpensesListToJson(expensesList: List<Expense>): String = gson.toJson(expensesList)

    fun importExpensesListFromJson(expensesListJson: String): List<Expense> =
        gson.fromJson(expensesListJson, object : TypeToken<List<Expense>>() {}.type)
}