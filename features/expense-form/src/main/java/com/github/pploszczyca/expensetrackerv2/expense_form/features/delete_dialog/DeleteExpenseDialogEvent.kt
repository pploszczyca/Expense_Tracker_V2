package com.github.pploszczyca.expensetrackerv2.expense_form.features.delete_dialog

import com.github.pploszczyca.expensetrackerv2.domain.Expense

sealed interface DeleteExpenseDialogEvent {
    data class ExpenseChanged(val expense: Expense) : DeleteExpenseDialogEvent
    data object ConfirmButtonClick : DeleteExpenseDialogEvent
}