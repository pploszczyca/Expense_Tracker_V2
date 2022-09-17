package com.example.expensetrackerv2.extensions

import com.example.expensetrackerv2.database.models.Type
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.utilities.MathUtils

fun List<ExpenseWithCategory>.totalIncomeAsString(): String =
    this.totalSumByTypeAsString(type = Type.INCOME)

fun List<ExpenseWithCategory>.totalOutgoAsString(): String =
    this.totalSumByTypeAsString(type = Type.OUTGO)

private fun List<ExpenseWithCategory>.totalSumByTypeAsString(type: Type): String =
    MathUtils.sumMoneyInListToString(
        expenseWithCategoryList = this.filter { it.type == type }
    )
