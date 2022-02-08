package com.example.expensetrackerv2.ui.type_of_expense_settings

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.database.models.TypeOfExpense

@Composable
fun TypeOfExpenseDeleteDialog(
    typeOfExpense: TypeOfExpense,
    onConfirmButtonClick: (TypeOfExpense) -> Unit,
    onDismissButtonClick: () -> Unit
) {
    AlertDialog(
        title = { Text(text = stringResource(id = R.string.delete_type_of_expense_title)) },

        text = { Text(text = stringResource(id = R.string.delete_type_of_expense_question)) },

        confirmButton = {
            TextButton(onClick = { onConfirmButtonClick(typeOfExpense) }) {
                Text(text = stringResource(id = R.string.delete))
            }
        },

        dismissButton = {
            TextButton(onClick = { onDismissButtonClick() }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },

        onDismissRequest = { onDismissButtonClick() })
}