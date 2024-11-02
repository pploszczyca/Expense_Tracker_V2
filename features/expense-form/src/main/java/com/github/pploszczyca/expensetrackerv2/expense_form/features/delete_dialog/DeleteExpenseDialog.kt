package com.github.pploszczyca.expensetrackerv2.expense_form.features.delete_dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.github.pploszczyca.expensetrackerv2.features.expense_form.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.pploszczyca.expensetrackerv2.domain.Expense

@Composable
fun DeleteExpenseAlertDialog(
    isDialogVisible: Boolean,
    expenseToDelete: Expense,
    onDismissClick: () -> Unit,
    onConfirmButtonClick: () -> Unit,
) {
    val viewModel: DeleteExpenseDialogViewModel = hiltViewModel()

    viewModel.onEvent(DeleteExpenseDialogEvent.ExpenseChanged(expenseToDelete))
    if (isDialogVisible) {
        AlertDialog(onDismissRequest = onDismissClick,
            title = { Text(stringResource(id = R.string.delete_expense_title)) },
            text = { Text(stringResource(id = R.string.delete_expense_question)) },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.onEvent(DeleteExpenseDialogEvent.ConfirmButtonClick)
                    onConfirmButtonClick()
                }) {
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
}
