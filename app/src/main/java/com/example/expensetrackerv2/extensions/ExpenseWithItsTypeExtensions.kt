package com.example.expensetrackerv2.extensions

import com.example.expensetrackerv2.database.models.Type
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.utilities.MathUtils

fun List<ExpenseWithItsType>.totalIncomeAsString(): String =
    this.totalSumByTypeAsString(type = Type.INCOME)

fun List<ExpenseWithItsType>.totalOutgoAsString(): String =
    this.totalSumByTypeAsString(type = Type.OUTGO)

private fun List<ExpenseWithItsType>.totalSumByTypeAsString(type: Type): String =
    MathUtils.sumMoneyInListToString(
        expenseWithItsTypeList = this.filter { it.type == type }
    )
