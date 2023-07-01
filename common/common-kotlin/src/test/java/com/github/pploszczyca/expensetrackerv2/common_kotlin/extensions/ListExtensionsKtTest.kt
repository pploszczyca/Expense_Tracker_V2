package com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class ListExtensionsKtTest : BehaviorSpec({

    val listOfStrings = listOf("test", "hello", "second hello")

    Given("False condition") {
        val condition = false

        When("Want to filter") {
            val result = listOfStrings.filterIf(condition) {
                it == "hello"
            }

            Then("The list should be the same") {
                result shouldBe listOfStrings
            }
        }
    }

    Given("True condition") {
        val condition = true

        When("Want to filter") {
            val result = listOfStrings.filterIf(condition) {
                it == "hello"
            }

            Then("The list should be filtered") {
                result shouldBe listOf("hello")
            }
        }
    }
})
