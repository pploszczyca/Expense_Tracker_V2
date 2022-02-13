package com.example.expensetrackerv2.utilities

import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JSONUtils {
    private val gson = Gson()

    fun exportExpensesListToJson(expensesList: List<ExpenseWithItsType>): String =
        gson.toJson(expensesList)

    fun importExpensesListFromJson(expensesListJson: String): List<ExpenseWithItsType> =
        gson.fromJson(expensesListJson, object : TypeToken<List<ExpenseWithItsType>>() {}.type)
}