package com.example.expensetrackerv2.ui.main.features.filter_dialog

import com.example.expensetrackerv2.database.models.view_models.ExpenseMonthYearKey

sealed interface MainFilterDialogEvent {
    data class OptionSelected(val key: ExpenseMonthYearKey): MainFilterDialogEvent
    object CloseDialog: MainFilterDialogEvent
    object ResetSelection: MainFilterDialogEvent
}
