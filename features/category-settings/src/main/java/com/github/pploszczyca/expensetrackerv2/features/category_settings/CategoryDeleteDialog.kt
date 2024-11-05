package com.github.pploszczyca.expensetrackerv2.features.category_settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.github.pploszczyca.expensetrackerv2.common_ui.dialog.Dialog

@Composable
fun CategoryDeleteDialog(
    isDialogVisible: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(
        isDialogVisible = isDialogVisible,
        title = stringResource(id = R.string.delete_type_of_expense_title),
        description = stringResource(id = R.string.delete_type_of_expense_question),
        confirmButtonText = stringResource(id = R.string.delete),
        dismissButtonText = stringResource(id = R.string.cancel),
        onDismiss = onDismiss,
        onConfirm = onConfirm,
    )
}