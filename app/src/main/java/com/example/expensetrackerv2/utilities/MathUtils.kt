package com.example.expensetrackerv2.utilities

import com.example.expensetrackerv2.database.models.Type
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithCategory
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
        roundToMoney(expenseWithCategoryList.map { expense -> expense.price * expense.type.multiplier }
            .sum())

    fun sumMoneyInListToString(expenseWithCategoryList: List<ExpenseWithCategory>): String =
        decimalFormat.format(
            sumMoneyInList(expenseWithCategoryList)
        )

    fun sumMoneyInListByTypeToString(
        expenseWithCategoryList: List<ExpenseWithCategory>,
        type: Type
    ): String = sumMoneyInListToString(expenseWithCategoryList.filter { it.type == type })
}