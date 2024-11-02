package com.github.pploszczyca.expensetrackerv2.expense_form.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.github.pploszczyca.expensetrackerv2.common_ui.dialog.Dialog
import com.github.pploszczyca.expensetrackerv2.expense_form.view_model.ExpenseFormViewModel
import com.github.pploszczyca.expensetrackerv2.features.expense_form.R

@Composable
fun DeleteExpenseDialog(
    viewState: ExpenseFormViewModel.ViewState,
    onDismissDeleteDialog: () -> Unit = {},
    onConfirmDeleteDialog: () -> Unit = {},
) {
    Dialog(
        isDialogVisible = viewState.shouldShowDeleteDialog,
        title = stringResource(id = R.string.delete_expense_title),
        description = stringResource(id = R.string.delete_expense_question),
        confirmButtonText = stringResource(id = R.string.yes),
        dismissButtonText = stringResource(id = R.string.no),
        onDismiss = { onDismissDeleteDialog() },
        onConfirm = { onConfirmDeleteDialog() },
    )
}