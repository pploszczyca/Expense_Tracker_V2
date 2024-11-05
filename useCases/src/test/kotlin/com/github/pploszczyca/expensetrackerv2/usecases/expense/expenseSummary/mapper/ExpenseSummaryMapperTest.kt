package com.github.pploszczyca.expensetrackerv2.usecases.expense.expenseSummary.mapper

import com.github.pploszczyca.expensetrackerv2.common_test.dummy
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseDate
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseSummary
import com.github.pploszczyca.expensetrackerv2.domain.Price
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate

class ExpenseSummaryMapperTest : BehaviorSpec({

    fun tested(): ExpenseSummaryMapper = ExpenseSummaryMapper()

    Given("List of expenses") {
        val firstDate = LocalDate.of(2023, 7, 9).let(ExpenseDate::of)
        val secondDate = LocalDate.of(2023, 8, 11).let(ExpenseDate::of)
        val thirdDate = LocalDate.of(2022, 6, 11).let(ExpenseDate::of)
        val firstExpense: Expense = fakeExpense(
            date = firstDate,
            price = Price.of(30.0),
            type = Expense.Type.Income
        )
        val secondExpense: Expense = fakeExpense(
            date = secondDate,
            price = Price.of(40.0),
            type = Expense.Type.Outgo
        )
        val thirdExpense: Expense = fakeExpense(
            date = thirdDate,
            price = Price.of(50.0),
            type = Expense.Type.Outgo
        )
        val expenses = listOf(
            firstExpense,
            secondExpense,
            thirdExpense,
        )
        val firstYearlyExpenses = ExpenseSummary.YearlyExpense(
            year = 2023,
            monthlyExpenses = listOf(
                ExpenseSummary.YearlyExpense.MonthlyExpense(
                    month = 7,
                    dailyExpenses = listOf(
                        ExpenseSummary.YearlyExpense.MonthlyExpense.DailyExpense(
                            day = 9,
                            expenses = listOf(firstExpense),
                        ),
                    ),
                ),
                ExpenseSummary.YearlyExpense.MonthlyExpense(
                    month = 8,
                    dailyExpenses = listOf(
                        ExpenseSummary.YearlyExpense.MonthlyExpense.DailyExpense(
                            day = 11,
                            expenses = listOf(secondExpense),
                        ),
                    ),
                )
            ),
        )
        val secondYearlyExpenses = ExpenseSummary.YearlyExpense(
            year = 2022,
            monthlyExpenses = listOf(
                ExpenseSummary.YearlyExpense.MonthlyExpense(
                    month = 6,
                    dailyExpenses = listOf(
                        ExpenseSummary.YearlyExpense.MonthlyExpense.DailyExpense(
                            day = 11,
                            expenses = listOf(thirdExpense),
                        ),
                    ),
                )
            ),
        )
        val expenseSummary = ExpenseSummary(
            yearlyExpenses = listOf(
                firstYearlyExpenses,
                secondYearlyExpenses,
            ),
        )

        When("Expenses are mapped") {
            val actual = tested().toExpenseSummary(expenses = expenses)

            Then("Should return Expense Summary") {
                actual shouldBe expenseSummary
            }
        }
    }
})

private fun fakeExpense(
    date: ExpenseDate = dummy(),
    price: Price = dummy(),
    type: Expense.Type = dummy(),
): Expense =
    mockk {
        every { this@mockk.date } returns date
        every { this@mockk.price } returns price
        every { this@mockk.type } returns type
    }
