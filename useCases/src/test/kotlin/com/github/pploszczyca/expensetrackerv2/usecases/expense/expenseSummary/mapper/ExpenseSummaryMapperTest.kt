package com.github.pploszczyca.expensetrackerv2.usecases.expense.expenseSummary.mapper

import com.github.pploszczyca.expensetrackerv2.common_kotlin.extensions.toDate
import com.github.pploszczyca.expensetrackerv2.domain.Category
import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseSummary
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDate
import java.util.Date

class ExpenseSummaryMapperTest : BehaviorSpec({

    fun tested(): ExpenseSummaryMapper = ExpenseSummaryMapper()

    Given("List of expenses") {
        val firstDate = LocalDate.of(2023, 7, 9).toDate()
        val secondDate = LocalDate.of(2023, 8, 11).toDate()
        val thirdDate = LocalDate.of(2022, 6, 11).toDate()
        val firstExpense: Expense = fakeExpense(date = firstDate)
        val secondExpense: Expense = fakeExpense(date = secondDate)
        val thirdExpense: Expense = fakeExpense(date = thirdDate)
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
                            expenses = listOf(firstExpense)
                        ),
                    )
                ),
                ExpenseSummary.YearlyExpense.MonthlyExpense(
                    month = 8,
                    dailyExpenses = listOf(
                        ExpenseSummary.YearlyExpense.MonthlyExpense.DailyExpense(
                            day = 11,
                            expenses = listOf(secondExpense)
                        ),
                    )
                )
            )
        )
        val secondYearlyExpenses = ExpenseSummary.YearlyExpense(
            year = 2022,
            monthlyExpenses = listOf(
                ExpenseSummary.YearlyExpense.MonthlyExpense(
                    month = 6,
                    dailyExpenses = listOf(
                        ExpenseSummary.YearlyExpense.MonthlyExpense.DailyExpense(
                            day = 11,
                            expenses = listOf(thirdExpense)
                        ),
                    )
                )
            )
        )
        val expenseSummary = ExpenseSummary(
            yearlyExpenses = listOf(
                firstYearlyExpenses,
                secondYearlyExpenses,
            )
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
    date: Date = Date(),
    price: Double = 42.0,
    categoryType: Category.Type = Category.Type.INCOME,
): Expense =
    mockk {
        every { this@mockk.date } returns date
        every { this@mockk.price } returns price
        every { this@mockk.category } returns fakeCategory(categoryType = categoryType)
    }

private fun fakeCategory(categoryType: Category.Type) =
    mockk<Category> {
        every { this@mockk.type } returns categoryType
    }
