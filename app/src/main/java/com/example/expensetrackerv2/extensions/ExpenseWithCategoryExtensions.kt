package com.example.expensetrackerv2.extensions

import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import java.math.RoundingMode
import java.text.DecimalFormat


private const val DECIMAL_PATTERN: String = "#.##"
private val decimalFormat = DecimalFormat(DECIMAL_PATTERN).apply {
    roundingMode = RoundingMode.CEILING
}

fun List<Expense>.totalIncomeAsString(): String =
    this.totalSumByTypeAsString(categoryType = Category.Type.INCOME)

fun List<Expense>.totalOutgoAsString(): String =
    this.totalSumByTypeAsString(categoryType = Category.Type.OUTGO)

private fun List<Expense>.totalSumByTypeAsString(categoryType: Category.Type): String =
    this.filter { it.category.type == categoryType }
        .sumOf { expense -> expense.price * expense.category.type.multiplier }
        .let(decimalFormat::format)
