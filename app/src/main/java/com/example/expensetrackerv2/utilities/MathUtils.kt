package com.example.expensetrackerv2.utilities

import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import kotlin.math.round

object MathUtils {
    fun roundToMoney(value: Double): Double = round(value * 100) / 100

    fun sumMoneyInList(expenseWithItsTypeList: List<ExpenseWithItsType>) =
        roundToMoney(expenseWithItsTypeList.map { expense -> expense.price * expense.type.multiplier }
            .sum())
}