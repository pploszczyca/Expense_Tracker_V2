package com.example.expensetrackerv2.extensions

import com.example.expensetrackerv2.models.CategoryType
import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.utilities.MathUtils

fun List<ExpenseWithCategory>.totalIncomeAsString(): String =
    this.totalSumByTypeAsString(categoryType = CategoryType.INCOME)

fun List<ExpenseWithCategory>.totalOutgoAsString(): String =
    this.totalSumByTypeAsString(categoryType = CategoryType.OUTGO)

private fun List<ExpenseWithCategory>.totalSumByTypeAsString(categoryType: CategoryType): String =
    MathUtils.sumMoneyInListToString(
        expenseWithCategoryList = this.filter { it.categoryType == categoryType }
    )
