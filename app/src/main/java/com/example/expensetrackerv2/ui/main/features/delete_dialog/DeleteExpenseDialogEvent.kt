package com.example.expensetrackerv2.ui.main.features.delete_dialog

sealed interface DeleteExpenseDialogEvent {
    object ConfirmButtonClick : DeleteExpenseDialogEvent
}