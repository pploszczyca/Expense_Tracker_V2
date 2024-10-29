package com.github.pploszczyca.expensetrackerv2.domain

import java.math.BigDecimal
import java.math.RoundingMode

@JvmInline
value class Price private constructor(private val amount: BigDecimal) {
    companion object {
        fun of(amount: BigDecimal): Price =
            amount
                .setScale(2, RoundingMode.HALF_UP)
                .let(::Price)
    }

    operator fun plus(other: Price): Price = of(this.amount + other.amount)
    operator fun minus(other: Price): Price = of(this.amount - other.amount)
    operator fun times(multiplier: BigDecimal): Price = of(this.amount * multiplier)

    override fun toString(): String = "$amount"
}

inline fun <T> Iterable<T>.sumOf(selector: (T) -> Price): Price =
    this.fold(Price.of(BigDecimal.ZERO)) { acc, element -> acc + selector(element) }