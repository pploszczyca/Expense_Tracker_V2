package com.github.pploszczyca.expensetrackerv2.domain

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk

class ExpenseSummaryTest : BehaviorSpec({

    Given("List of expenses for single day") {
        val expenses = listOf(
            fakeExpense(price = 20.0, categoryType = Category.Type.INCOME),
            fakeExpense(price = 30.0, categoryType = Category.Type.INCOME),
            fakeExpense(price = 50.0, categoryType = Category.Type.INCOME),
            fakeExpense(price = 40.0, categoryType = Category.Type.OUTGO),
            fakeExpense(price = 60.0, categoryType = Category.Type.OUTGO),
            fakeExpense(price = 70.0, categoryType = Category.Type.OUTGO),
        )

        and("Single daily expense") {
            val dailyExpense = ExpenseSummary.YearlyExpense.MonthlyExpense.DailyExpense(
                day = 9,
                expenses = expenses,
            )

            When("Calculate total daily income") {
                val totalIncome = dailyExpense.totalIncome

                Then("Should equal to sum of all income expenses") {
                    totalIncome shouldBe 100.0
                }
            }

            When("Calculate total daily outgo") {
                val totalIncome = dailyExpense.totalOutgo

                Then("Should equal to sum of all outgo expenses") {
                    totalIncome shouldBe 170.0
                }
            }
        }
    }

    Given("List of expenses for multiple days") {
        val expensesForFirstDay = listOf(
            fakeExpense(price = 20.0, categoryType = Category.Type.INCOME),
            fakeExpense(price = 30.0, categoryType = Category.Type.INCOME),
            fakeExpense(price = 50.0, categoryType = Category.Type.INCOME),
            fakeExpense(price = 40.0, categoryType = Category.Type.OUTGO),
            fakeExpense(price = 60.0, categoryType = Category.Type.OUTGO),
            fakeExpense(price = 70.0, categoryType = Category.Type.OUTGO),
        )
        val expensesForSecondDay = listOf(
            fakeExpense(price = 10.0, categoryType = Category.Type.INCOME),
            fakeExpense(price = 60.0, categoryType = Category.Type.INCOME),
            fakeExpense(price = 30.0, categoryType = Category.Type.INCOME),
            fakeExpense(price = 20.0, categoryType = Category.Type.OUTGO),
            fakeExpense(price = 40.0, categoryType = Category.Type.OUTGO),
            fakeExpense(price = 90.0, categoryType = Category.Type.OUTGO),
        )

        and("Daily expenses") {
            val firstDailyExpense = ExpenseSummary.YearlyExpense.MonthlyExpense.DailyExpense(
                day = 1,
                expenses = expensesForFirstDay,
            )
            val secondDailyExpense = ExpenseSummary.YearlyExpense.MonthlyExpense.DailyExpense(
                day = 2,
                expenses = expensesForSecondDay,
            )

            and("Single mothly expense") {
                val monthlyExpense = ExpenseSummary.YearlyExpense.MonthlyExpense(
                    month = 1,
                    dailyExpenses = listOf(firstDailyExpense, secondDailyExpense)
                )

                When("Calculate total monthly income") {
                    val totalIncome = monthlyExpense.totalIncome

                    Then("Should equal to sum of all income expenses") {
                        totalIncome shouldBe 200.0
                    }
                }

                When("Calculate total monthly outgo") {
                    val totalOutgo = monthlyExpense.totalOutgo

                    Then("Should equal to sum of all outgo expenses") {
                        totalOutgo shouldBe 320.0
                    }
                }
            }
        }
    }

    Given("Monthly expenses") {
        val monthlyExpenses = listOf(
            fakeMonthlyExpense(totalIncome = 20.0, totalOutgo = 10.0),
            fakeMonthlyExpense(totalIncome = 30.0, totalOutgo = 20.0),
            fakeMonthlyExpense(totalIncome = 10.0, totalOutgo = 10.0),
            fakeMonthlyExpense(totalIncome = 50.0, totalOutgo = 50.0),
            fakeMonthlyExpense(totalIncome = 10.0, totalOutgo = 20.0),
        )

        and("Single yearly expense") {
            val yearlyExpense = ExpenseSummary.YearlyExpense(
                year = 2023,
                monthlyExpenses = monthlyExpenses,
            )

            When("Calculate total yearly income") {
                val totalIncome = yearlyExpense.totalIncome

                Then("Should equal to sum of all income expenses") {
                    totalIncome shouldBe 120.0
                }
            }

            When("Calculate total yearly outgo") {
                val totalOutgo = yearlyExpense.totalOutgo

                Then("Should equal to sum of all outgo expenses") {
                    totalOutgo shouldBe 110.0
                }
            }
        }
    }

    Given("Yearly expenses") {
        val yearlyExpenses = listOf(
            fakeYearlyExpense(totalIncome = 20.0, totalOutgo = 10.0),
            fakeYearlyExpense(totalIncome = 60.0, totalOutgo = 90.0),
            fakeYearlyExpense(totalIncome = 70.0, totalOutgo = 60.0),
            fakeYearlyExpense(totalIncome = 30.0, totalOutgo = 40.0),
            fakeYearlyExpense(totalIncome = 20.0, totalOutgo = 5.00),
        )

        and("Expense summary") {
            val expenseSummary = ExpenseSummary(
                yearlyExpenses = yearlyExpenses,
            )

            When("Calculate total income") {
                val totalIncome = expenseSummary.totalIncome

                Then("Should equal to sum of all income expenses") {
                    totalIncome shouldBe 200.0
                }
            }

            When("Calculate total outgo") {
                val totalOutgo = expenseSummary.totalOutgo

                Then("Should equal to sum of all income expenses") {
                    totalOutgo shouldBe 205.0
                }
            }
        }
    }
})

private fun fakeExpense(
    price: Double = 42.0,
    categoryType: Category.Type = Category.Type.INCOME,
): Expense =
    mockk {
        every { this@mockk.price } returns price
        every { this@mockk.category } returns fakeCategory(categoryType = categoryType)
    }

private fun fakeCategory(categoryType: Category.Type) =
    mockk<Category> {
        every { this@mockk.type } returns categoryType
    }

private fun fakeMonthlyExpense(
    totalIncome: Double,
    totalOutgo: Double,
): ExpenseSummary.YearlyExpense.MonthlyExpense =
    mockk {
        every { this@mockk.totalIncome } returns totalIncome
        every { this@mockk.totalOutgo } returns totalOutgo
    }

private fun fakeYearlyExpense(
    totalIncome: Double,
    totalOutgo: Double,
): ExpenseSummary.YearlyExpense =
    mockk {
        every { this@mockk.totalIncome } returns totalIncome
        every { this@mockk.totalOutgo } returns totalOutgo
    }
