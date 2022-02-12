package com.example.expensetrackerv2.utilities

import com.example.expensetrackerv2.database.models.Type
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.round

object MathUtils {
    private val decimalFormat = DecimalFormat("#.##")

    init {
        decimalFormat.roundingMode = RoundingMode.CEILING
    }

    private fun roundToMoney(value: Double): Double = round(value * 100) / 100

    fun sumMoneyInList(expenseWithItsTypeList: List<ExpenseWithItsType>) =
        roundToMoney(expenseWithItsTypeList.map { expense -> expense.price * expense.type.multiplier }
            .sum())

    fun sumMoneyInListToString(expenseWithItsTypeList: List<ExpenseWithItsType>): String =
        decimalFormat.format(
            sumMoneyInList(expenseWithItsTypeList)
        )

    fun sumMoneyInListByTypeToString(
        expenseWithItsTypeList: List<ExpenseWithItsType>,
        type: Type
    ): String = sumMoneyInListToString(expenseWithItsTypeList.filter { it.type == type })
}