package com.example.expensetrackerv2.ui.main.features.list

import com.example.expensetrackerv2.database.models.view_models.ExpenseWithCategory

sealed interface ExpensesListEvent {
    data class ExpensesChanged(val expenses: List<ExpenseWithCategory>) : ExpensesListEvent
}