package com.github.pploszczyca.expensetrackerv2.features.main.features.list

import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.features.main.extensions.toStringDate
import com.github.pploszczyca.expensetrackerv2.features.main.extensions.totalIncomeAsString
import com.github.pploszczyca.expensetrackerv2.features.main.extensions.totalOutgoAsString
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ExpensesListGroupedExpensesMapper @Inject constructor() {
    fun map(expenses: List<Expense>): List<ExpenseListViewModel.ViewState.GroupedExpenses> =
        expenses
            .groupBy { it.monthYearKey }
            .map { (key, expenses) ->
                ExpenseListViewModel.ViewState.GroupedExpenses(
                    key = key,
                    dateText = key.toStringDate(),
                    expenses = expenses,
                    totalIncome = expenses.totalIncomeAsString(),
                    totalOutgo = expenses.totalOutgoAsString(),
                )
            }.toList()
}