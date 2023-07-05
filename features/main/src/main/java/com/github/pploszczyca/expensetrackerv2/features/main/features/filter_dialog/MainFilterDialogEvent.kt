package com.github.pploszczyca.expensetrackerv2.features.main.features.filter_dialog

import com.github.pploszczyca.expensetrackerv2.domain.Expense

sealed interface MainFilterDialogEvent {
    data class OptionSelected(val key: Expense.MonthYearKey) : MainFilterDialogEvent
    object CloseDialog : MainFilterDialogEvent
    object ResetSelection : MainFilterDialogEvent
}