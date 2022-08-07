package com.example.expensetrackerv2.ui.main.features.delete_dialog

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.expensetrackerv2.R

@Composable
fun DeleteExpenseAlertDialog(
    onDismissClick: () -> Unit,
    onConfirmButtonClick: () -> Unit
) {
    AlertDialog(onDismissRequest = onDismissClick,
        title = { Text(stringResource(id = R.string.delete_expense_title)) },
        text = { Text(stringResource(id = R.string.delete_expense_question)) },
        confirmButton = {
            TextButton(onClick = onConfirmButtonClick) {
                Text(text = stringResource(id = R.string.yes))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismissClick) {
                Text(text = stringResource(id = R.string.no))
            }
        }
    )
}
