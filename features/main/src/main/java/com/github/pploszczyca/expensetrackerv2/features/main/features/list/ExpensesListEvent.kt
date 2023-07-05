package com.github.pploszczyca.expensetrackerv2.features.main.features.list

import com.github.pploszczyca.expensetrackerv2.domain.Expense

sealed interface ExpensesListEvent {
    data class ExpensesChanged(val expenses: List<Expense>) : ExpensesListEvent
    data class OnEditExpenseButtonClicked(val expense: Expense) : ExpensesListEvent
}