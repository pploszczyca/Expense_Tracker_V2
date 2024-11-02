package com.github.pploszczyca.expensetrackerv2.domain

import java.math.BigDecimal
import java.math.RoundingMode

@JvmInline
value class Price private constructor(val amount: BigDecimal) {
    companion object {
        fun of(amount: BigDecimal): Price =
            amount
                .setScale(2, RoundingMode.HALF_UP)
                .let(::Price)

        fun of(amount: String): Price = of(BigDecimal(amount))

        fun of(amount: Double): Price = of(BigDecimal(amount))

        val ZERO: Price get() = of(BigDecimal.ZERO)
        val ONE: Price get() = of(BigDecimal.ONE)
        val MINUS_ONE: Price get() = of(BigDecimal.ONE.negate())
    }

    operator fun plus(other: Price): Price = of(this.amount + other.amount)
    operator fun minus(other: Price): Price = of(this.amount - other.amount)
    operator fun times(multiplier: Price): Price = of(this.amount * multiplier.amount)

    override fun toString(): String = "$amount"
}

inline fun <T> Iterable<T>.sumOf(selector: (T) -> Price): Price =
    this.fold(Price.of(BigDecimal.ZERO)) { acc, element -> acc + selector(element) }

fun Price?.orZero(): Price = this ?: Price.ZERO