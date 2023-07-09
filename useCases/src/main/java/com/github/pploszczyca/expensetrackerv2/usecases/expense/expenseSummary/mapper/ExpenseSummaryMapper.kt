package com.github.pploszczyca.expensetrackerv2.usecases.expense.expenseSummary.mapper

import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseSummary

class ExpenseSummaryMapper {
    fun toExpenseSummary(expenses: List<Expense>): ExpenseSummary =
        ExpenseSummary(
            yearlyExpenses = getYearlyExpenses(expenses = expenses),
        )

    private fun getYearlyExpenses(expenses: List<Expense>) =
        expenses.groupByYear().map { (year, expensesForYear) ->
            ExpenseSummary.YearlyExpense(
                year = year,
                monthlyExpenses = getMonthlyExpenses(expensesForYear = expensesForYear),
            )
        }

    private fun List<Expense>.groupByYear(): Map<Int, List<Expense>> =
        this.groupBy { it.date.year + YEAR_INDEX_ADJUSTMENT }

    private fun getMonthlyExpenses(expensesForYear: List<Expense>) =
        expensesForYear.groupByMonth()
            .map { (month, expenseForMonth) ->
                ExpenseSummary.YearlyExpense.MonthlyExpense(
                    month = month,
                    dailyExpenses = getDailyExpenses(expenseForMonth = expenseForMonth),
                )
            }

    private fun List<Expense>.groupByMonth(): Map<Int, List<Expense>> =
        this.groupBy { it.date.month + MONTH_INDEX_ADJUSTMENT }

    private fun getDailyExpenses(expenseForMonth: List<Expense>) =
        expenseForMonth.groupByDay()
            .map { (day, expenseForDay) ->
                ExpenseSummary.YearlyExpense.MonthlyExpense.DailyExpense(
                    day = day,
                    expenses = expenseForDay,
                )
            }

    private fun List<Expense>.groupByDay(): Map<Int, List<Expense>> =
        this.groupBy { it.date.date }

    private companion object {
        const val YEAR_INDEX_ADJUSTMENT = 1900
        const val MONTH_INDEX_ADJUSTMENT = 1
    }
}