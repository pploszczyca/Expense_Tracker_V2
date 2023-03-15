package com.example.expensetrackerv2.utilities

import com.example.expensetrackerv2.models.CategoryType
import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
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

    fun sumMoneyInList(expenseWithCategoryList: List<ExpenseWithCategory>) =
        roundToMoney(expenseWithCategoryList.map { expense -> expense.price * expense.categoryType.multiplier }
            .sum())

    fun sumMoneyInListToString(expenseWithCategoryList: List<ExpenseWithCategory>): String =
        decimalFormat.format(
            sumMoneyInList(expenseWithCategoryList)
        )

    fun sumMoneyInListByTypeToString(
        expenseWithCategoryList: List<ExpenseWithCategory>,
        categoryType: CategoryType
    ): String = sumMoneyInListToString(expenseWithCategoryList.filter { it.categoryType == categoryType })
}