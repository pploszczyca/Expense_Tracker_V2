package com.example.expensetrackerv2.ui.main

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.res.stringResource
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.database.models.view_models.ExpenseWithItsType
import com.example.expensetrackerv2.database.repositories.ExpenseWithItsTypeRepository
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun DeleteExpenseAlertDialog(
    isDeleteDialogOpen: MutableState<Boolean>,
    expenseWithItsTypeRepository: ExpenseWithItsTypeRepository,
    expenseWithItsType: MutableState<ExpenseWithItsType>
) {
    if (isDeleteDialogOpen.value) {
        AlertDialog(onDismissRequest = { isDeleteDialogOpen.value = false },
            title = { Text(stringResource(id = R.string.delete_expense_title)) },
            text = { Text(stringResource(id = R.string.delete_expense_question)) },
            confirmButton = {
                TextButton(onClick = {
                    runBlocking {
                        launch {
                            expenseWithItsTypeRepository.deleteExpense(expenseWithItsType.value)
                        }
                    }
                    isDeleteDialogOpen.value = false
                }) {
                    Text(text = stringResource(id = R.string.yes))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    isDeleteDialogOpen.value = false
                }) {
                    Text(text = stringResource(id = R.string.no))
                }
            }
        )
    }
}
