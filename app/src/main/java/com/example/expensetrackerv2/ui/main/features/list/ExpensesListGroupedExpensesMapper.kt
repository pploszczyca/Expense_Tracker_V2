package com.example.expensetrackerv2.ui.main.features.list

import com.example.expensetrackerv2.extensions.toStringDate
import com.example.expensetrackerv2.extensions.totalIncomeAsString
import com.example.expensetrackerv2.extensions.totalOutgoAsString
import com.github.pploszczyca.expensetrackerv2.domain.Expense
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