package com.github.pploszczyca.expensetrackerv2.common_kotlin.currencyFormatter

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import java.math.BigDecimal
import io.kotest.data.forAll
import io.kotest.data.row

class CurrencyFormatterTest : BehaviorSpec({

    val currencyFormatter = CurrencyFormatter()

    Given("Currency formatter") {
        forAll(
            row("1", BigDecimal("1.00")),
            row("0.1", BigDecimal("0.01")),
            row("0.01", BigDecimal("0.01")),
            row("0.001", BigDecimal("0.01")),
            row("0.0009", BigDecimal("0.09")),
            row("12.345", BigDecimal("123.45")),
        ) { amount, expected ->
            And("amount: $amount") {
                When("Amount is formatted") {
                    val result = currencyFormatter.format(amount)

                    Then("Should return properly formatted amount") {
                        result shouldBe expected
                    }
                }
            }
        }
    }
})