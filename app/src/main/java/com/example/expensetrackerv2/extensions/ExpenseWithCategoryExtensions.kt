package com.example.expensetrackerv2.extensions

import com.example.expensetrackerv2.utilities.MathUtils
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense

fun List<Expense>.totalIncomeAsString(): String =
    this.totalSumByTypeAsString(categoryType = Category.Type.INCOME)

fun List<Expense>.totalOutgoAsString(): String =
    this.totalSumByTypeAsString(categoryType = Category.Type.OUTGO)

private fun List<Expense>.totalSumByTypeAsString(categoryType: Category.Type): String =
    MathUtils.sumMoneyInListToString(
        expenseWithCategoryList = this.filter { it.category.type == categoryType }
    )
