package com.github.pploszczyca.expensetrackerv2.features.main.features.delete_dialog

sealed interface DeleteExpenseDialogEvent {
    object ConfirmButtonClick : DeleteExpenseDialogEvent
}