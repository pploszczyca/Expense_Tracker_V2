package com.github.pploszczyca.expensetrackerv2.usecases.expense.expenseSummary.mapper

import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.domain.ExpenseSummary

class ExpenseSummaryMapper {
    fun toExpenseSummary(expenses: List<Expense>): ExpenseSummary =
        ExpenseSummary(
            yearlyExpenses = expenses.groupBy { it.date.year }.map { (year, expensesForYear) ->
                ExpenseSummary.YearlyExpense(
                    year = year,
                    monthlyExpenses = expensesForYear.groupBy { it.date.month }
                        .map { (month, expenseForMonth) ->
                            ExpenseSummary.YearlyExpense.MonthlyExpense(
                                month = month,
                                dailyExpenses = expenseForMonth.groupBy { it.date.date }
                                    .map { (day, expenseForDay) ->
                                        ExpenseSummary.YearlyExpense.MonthlyExpense.DailyExpense(
                                            day = day,
                                            expenses = expenseForDay,
                                        )
                                    }
                            )
                        }
                )
            }
        )
}