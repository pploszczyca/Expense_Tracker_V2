package com.github.pploszczyca.expensetrackerv2.common_kotlin.currencyFormatter

import java.math.BigDecimal
import java.math.RoundingMode

class CurrencyFormatter {
    fun format(amount: String): BigDecimal =
        BigDecimal(amount)
            .adjustScaleToCents()
            .setScale(2, RoundingMode.HALF_UP)

    private fun BigDecimal.adjustScaleToCents(): BigDecimal =
        when (this.scale()) {
            0 -> this
            else -> this.movePointRight(this.scale() - 2)
        }
}