package com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.data.forAll
import io.kotest.data.row
import io.kotest.matchers.shouldBe
import java.text.SimpleDateFormat
import java.time.LocalDate

class LocalDateExtensionsKtTest : BehaviorSpec({
    Given("a LocalDate") {

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z")

        forAll(
            row(LocalDate.of(2023, 7, 9), dateFormat.parse("2023-07-09 00:00:00 GMT")),
            row(LocalDate.of(2021, 12, 31), dateFormat.parse("2021-12-31 00:00:00 GMT")),
            row(LocalDate.of(2000, 1, 1), dateFormat.parse("2000-01-01 00:00:00 GMT"))
        ) { localDate, expectedDate ->

            When("convert LocalDate to Date") {
                val date = localDate.toDate()

                Then("should equal to the expected Date") {
                    date shouldBe expectedDate
                }
            }
        }
    }
})
