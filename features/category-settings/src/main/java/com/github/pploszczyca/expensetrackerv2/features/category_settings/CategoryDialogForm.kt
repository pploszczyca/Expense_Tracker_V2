package com.github.pploszczyca.expensetrackerv2.features.category_settings

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.github.pploszczyca.expensetrackerv2.common_ui.dialog.Dialog
import com.github.pploszczyca.expensetrackerv2.common_ui.expense_form_text_field.ExpenseFormTextField

@Composable
fun CategoryDialogForm(
    isDialogVisible: Boolean,
    categoryName: String,
    confirmButtonText: String,
    onNameChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    Dialog(
        isDialogVisible = isDialogVisible,
        title = stringResource(id = R.string.type_of_expense_form_title),
        text = {
            ExpenseFormTextField(
                value = categoryName,
                onValueChange = onNameChange,
                label = stringResource(id = R.string.name),
                focusOnLoad = isDialogVisible,
            )
        },
        confirmButtonText = confirmButtonText,
        dismissButtonText = stringResource(id = R.string.cancel),
        onDismiss = onDismiss,
        onConfirm = onConfirm,
    )
}