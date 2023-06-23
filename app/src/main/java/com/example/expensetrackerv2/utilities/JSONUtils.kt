package com.example.expensetrackerv2.utilities

import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JSONUtils {
    private val gson = Gson()

    fun exportExpensesListToJson(expensesList: List<ExpenseWithCategory>): String =
        gson.toJson(expensesList)

    fun importExpensesListFromJson(expensesListJson: String): List<ExpenseWithCategory> =
        gson.fromJson(expensesListJson, object : TypeToken<List<ExpenseWithCategory>>() {}.type)
}