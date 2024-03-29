package com.example.expensetrackerv2.ui.main.features.list

import com.example.expensetrackerv2.extensions.toStringDate
import com.example.expensetrackerv2.extensions.totalIncomeAsString
import com.example.expensetrackerv2.extensions.totalOutgoAsString
import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory
import com.example.expensetrackerv2.models.view_models.getKey
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class ExpensesListGroupedExpensesMapper @Inject constructor() {
    fun map(expensesWithItsType: List<ExpenseWithCategory>): List<ExpenseListViewModel.ViewState.GroupedExpenses> =
        expensesWithItsType
            .groupBy { it.getKey() }
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