package com.github.pploszczyca.expensetrackerv2.features.main.features.list

import com.github.pploszczyca.expensetrackerv2.domain.Expense
import com.github.pploszczyca.expensetrackerv2.features.main.MainViewModel
import kotlinx.coroutines.flow.Flow

sealed interface ExpensesListEvent {
    data class OnInit(val mainViewState: Flow<MainViewModel.ViewState>) : ExpensesListEvent

    data class OnEditExpenseButtonClicked(val expense: Expense) : ExpensesListEvent
}