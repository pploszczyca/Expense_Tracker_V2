package com.example.expensetrackerv2.ui.type_of_expense_settings

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.expensetrackerv2.R

@Composable
fun TypeOfExpenseDeleteDialog(
    modelView: TypeOfExpenseSettingsModelView
) {
    fun closeDialog() {
        modelView.onEvent(TypeOfExpenseSettingsEvent.CloseDeleteDialog())
    }

    AlertDialog(
        title = { Text(text = stringResource(id = R.string.delete_type_of_expense_title)) },

        text = { Text(text = stringResource(id = R.string.delete_type_of_expense_question)) },

        confirmButton = {
            TextButton(onClick = { modelView.onEvent(TypeOfExpenseSettingsEvent.DeleteDialogSubmit()) }) {
                Text(text = stringResource(id = R.string.delete))
            }
        },

        dismissButton = {
            TextButton(onClick = { closeDialog() }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },

        onDismissRequest = { closeDialog() })
}