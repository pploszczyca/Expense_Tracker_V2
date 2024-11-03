package com.github.pploszczyca.expensetrackerv2.domain

import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class ExpenseSummaryTest : BehaviorSpec({

    Given("List of expenses for single day") {
        val expenses = listOf(
            fakeExpense(price = Price.of(20.0), expenseType = Expense.Type.Income),
            fakeExpense(price = Price.of(30.0), expenseType = Expense.Type.Income),
            fakeExpense(price = Price.of(50.0), expenseType = Expense.Type.Income),
            fakeExpense(price = Price.of(40.0), expenseType = Expense.Type.Outgo),
            fakeExpense(price = Price.of(60.0), expenseType = Expense.Type.Outgo),
            fakeExpense(price = Price.of(70.0), expenseType = Expense.Type.Outgo),
        )

        and("Single daily expense") {
            val dailyExpense = ExpenseSummary.YearlyExpense.MonthlyExpense.DailyExpense(
                day = 9,
                expenses = expenses,
            )

            When("Calculate total daily income") {
                val totalIncome = dailyExpense.totalIncome

                Then("Should equal to sum of all income expenses") {
                    totalIncome shouldBe Price.of(100.00)
                }
            }

            When("Calculate total daily outgo") {
                val totalIncome = dailyExpense.totalOutgo

                Then("Should equal to sum of all outgo expenses") {
                    totalIncome shouldBe Price.of(170.00)
                }
            }
        }
    }

    Given("List of expenses for multiple days") {
        val expensesForFirstDay = listOf(
            fakeExpense(price = Price.of(20.0), expenseType = Expense.Type.Income),
            fakeExpense(price = Price.of(30.0), expenseType = Expense.Type.Income),
            fakeExpense(price = Price.of(50.0), expenseType = Expense.Type.Income),
            fakeExpense(price = Price.of(40.0), expenseType = Expense.Type.Outgo),
            fakeExpense(price = Price.of(60.0), expenseType = Expense.Type.Outgo),
            fakeExpense(price = Price.of(70.0), expenseType = Expense.Type.Outgo),
        )
        val expensesForSecondDay = listOf(
            fakeExpense(price = Price.of(10.0), expenseType = Expense.Type.Income),
            fakeExpense(price = Price.of(60.0), expenseType = Expense.Type.Income),
            fakeExpense(price = Price.of(30.0), expenseType = Expense.Type.Income),
            fakeExpense(price = Price.of(20.0), expenseType = Expense.Type.Outgo),
            fakeExpense(price = Price.of(40.0), expenseType = Expense.Type.Outgo),
            fakeExpense(price = Price.of(90.0), expenseType = Expense.Type.Outgo),
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
                        totalIncome shouldBe Price.of(200.00)
                    }
                }

                When("Calculate total monthly outgo") {
                    val totalOutgo = monthlyExpense.totalOutgo

                    Then("Should equal to sum of all outgo expenses") {
                        totalOutgo shouldBe Price.of(320.00)
                    }
                }
            }
        }
    }

    Given("Monthly expenses") {
        val monthlyExpenses = listOf(
            fakeMonthlyExpense(totalIncome = Price.of(20.0), totalOutgo = Price.of(10.0)),
            fakeMonthlyExpense(totalIncome = Price.of(30.0), totalOutgo = Price.of(20.0)),
            fakeMonthlyExpense(totalIncome = Price.of(10.0), totalOutgo = Price.of(10.0)),
            fakeMonthlyExpense(totalIncome = Price.of(50.0), totalOutgo = Price.of(50.0)),
            fakeMonthlyExpense(totalIncome = Price.of(10.0), totalOutgo = Price.of(20.0)),
        )

        and("Single yearly expense") {
            val yearlyExpense = ExpenseSummary.YearlyExpense(
                year = 2023,
                monthlyExpenses = monthlyExpenses,
            )

            When("Calculate total yearly income") {
                val totalIncome = yearlyExpense.totalIncome

                Then("Should equal to sum of all income expenses") {
                    totalIncome shouldBe Price.of(120.00)
                }
            }

            When("Calculate total yearly outgo") {
                val totalOutgo = yearlyExpense.totalOutgo

                Then("Should equal to sum of all outgo expenses") {
                    totalOutgo shouldBe Price.of(110.00)
                }
            }
        }
    }

    Given("Yearly expenses") {
        val yearlyExpenses = listOf(
            fakeYearlyExpense(totalIncome = Price.of(20.0), totalOutgo = Price.of(10.0)),
            fakeYearlyExpense(totalIncome = Price.of(60.0), totalOutgo = Price.of(90.0)),
            fakeYearlyExpense(totalIncome = Price.of(70.0), totalOutgo = Price.of(60.0)),
            fakeYearlyExpense(totalIncome = Price.of(30.0), totalOutgo = Price.of(40.0)),
            fakeYearlyExpense(totalIncome = Price.of(20.0), totalOutgo = Price.of(5.00)),
        )

        and("Expense summary") {
            val expenseSummary = ExpenseSummary(
                yearlyExpenses = yearlyExpenses,
            )

            When("Calculate total income") {
                val totalIncome = expenseSummary.totalIncome

                Then("Should equal to sum of all income expenses") {
                    totalIncome shouldBe Price.of(200.00)
                }
            }

            When("Calculate total outgo") {
                val totalOutgo = expenseSummary.totalOutgo

                Then("Should equal to sum of all income expenses") {
                    totalOutgo shouldBe Price.of(205.00)
                }
            }
        }
    }
})

private fun fakeExpense(
    price: Price = Price.of(42.0),
    expenseType: Expense.Type = Expense.Type.Income,
): Expense =
    Expense.new(
        title = "Title",
        price = price,
        date = ExpenseDate.now(),
        description = "Description",
        place = "Place",
        type = expenseType,
        category = null,
    )

private fun fakeMonthlyExpense(
    totalIncome: Price,
    totalOutgo: Price,
): ExpenseSummary.YearlyExpense.MonthlyExpense =
    ExpenseSummary.YearlyExpense.MonthlyExpense(
        month = 1,
        dailyExpenses = listOf(
            ExpenseSummary.YearlyExpense.MonthlyExpense.DailyExpense(
                day = 1,
                expenses = listOf(
                    fakeExpense(
                        price = totalIncome,
                        expenseType = Expense.Type.Income
                    )
                ),
            ),
            ExpenseSummary.YearlyExpense.MonthlyExpense.DailyExpense(
                day = 2,
                expenses = listOf(
                    fakeExpense(
                        price = totalOutgo,
                        expenseType = Expense.Type.Outgo
                    )
                ),
            )
        ),
    )

private fun fakeYearlyExpense(
    totalIncome: Price,
    totalOutgo: Price,
): ExpenseSummary.YearlyExpense =
    ExpenseSummary.YearlyExpense(
        year = 2023,
        monthlyExpenses = listOf(
            fakeMonthlyExpense(
                totalIncome = totalIncome,
                totalOutgo = totalOutgo
            )
        ),
    )
