package com.github.pploszczyca.expensetrackerv2.features.category_settings

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.expensetrackerv2.R

@Composable
fun CategoryDeleteDialog(
    modelView: CategorySettingsViewModel,
) {
    fun closeDialog() {
        modelView.onEvent(CategorySettingsEvent.CloseDeleteDialog)
    }

    AlertDialog(
        title = { Text(text = stringResource(id = R.string.delete_type_of_expense_title)) },

        text = { Text(text = stringResource(id = R.string.delete_type_of_expense_question)) },

        confirmButton = {
            TextButton(onClick = { modelView.onEvent(CategorySettingsEvent.DeleteDialogSubmit) }) {
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