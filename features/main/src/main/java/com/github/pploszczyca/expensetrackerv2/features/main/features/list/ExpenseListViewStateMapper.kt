package com.github.pploszczyca.expensetrackerv2.features.main.features.list

import com.github.pploszczyca.expensetrackerv2.domain.ExpenseSummary
import dagger.hilt.android.scopes.ViewModelScoped
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import javax.inject.Inject

@ViewModelScoped
class ExpenseListViewStateMapper @Inject constructor() {
    private val todayLocalDateProvider: () -> LocalDate = LocalDate::now
    private val formatter = DateTimeFormatter.ofPattern("dd MMMM", Locale.ENGLISH)

    fun toDailyExpenses(expenseSummary: ExpenseSummary): List<ExpenseListViewModel.ViewState.DailyExpense> =
        expenseSummary.yearlyExpenses.flatMap { yearlyExpenses ->
            yearlyExpenses.monthlyExpenses.flatMap { monthlyExpenses ->
                monthlyExpenses.dailyExpenses.map { dailyExpenses ->
                    ExpenseListViewModel.ViewState.DailyExpense(
                        date = toStringDateFormat(
                            year = yearlyExpenses.year,
                            month = monthlyExpenses.month,
                            day = dailyExpenses.day,
                        ),
                        expenses = dailyExpenses.expenses,
                        totalIncome = dailyExpenses.totalIncome.toString(),
                        totalOutgo = dailyExpenses.totalOutgo.toString(),
                    )
                }
            }
        }

    private fun toStringDateFormat(
        year: Int,
        month: Int,
        day: Int,
    ): String =
        when (val date = LocalDate.of(year, month, day)) {
            todayLocalDateProvider() -> "Today"
            else -> date.let(formatter::format).orEmpty()
        }
}
