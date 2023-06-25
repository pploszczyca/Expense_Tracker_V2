package com.example.expensetrackerv2.utilities

import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.round

object MathUtils {
    private const val DECIMAL_PATTERN: String = "#.##"
    private val decimalFormat = DecimalFormat(DECIMAL_PATTERN)

    init {
        decimalFormat.roundingMode = RoundingMode.CEILING
    }

    private fun roundToMoney(value: Double): Double = round(value * 100) / 100

    fun sumMoneyInList(expenses: List<Expense>): Double =
        roundToMoney(expenses.sumOf { expense -> expense.price * expense.category.type.multiplier })

    fun sumMoneyInListToString(expenseWithCategoryList: List<Expense>): String =
        decimalFormat.format(
            sumMoneyInList(expenseWithCategoryList)
        )

    fun sumMoneyInListByTypeToString(
        expenses: List<Expense>,
        categoryType: Category.Type,
    ): String =
        sumMoneyInListToString(expenses.filter { it.category.type == categoryType })
}