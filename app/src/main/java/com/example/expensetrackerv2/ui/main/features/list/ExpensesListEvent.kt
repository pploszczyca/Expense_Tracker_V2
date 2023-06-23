package com.example.expensetrackerv2.ui.main.features.list

import com.example.expensetrackerv2.models.view_models.ExpenseWithCategory

sealed interface ExpensesListEvent {
    data class ExpensesChanged(val expenses: List<ExpenseWithCategory>) : ExpensesListEvent
}