package com.github.pploszczyca.expensetrackerv2.features.category_settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.github.pploszczyca.expensetrackerv2.domain.Category

@Composable
fun CategoryDialogForm(
    modelView: CategorySettingsViewModel,
) {
    val name by modelView.name

    val confirmButtonTitle =
        stringResource(id = if (modelView.isThisNewCategory()) R.string.add else R.string.update)

    AlertDialog(
        title = { Text(text = stringResource(id = R.string.type_of_expense_form_title)) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    label = { Text(stringResource(id = R.string.name)) },
                    onValueChange = { modelView.onEvent(CategorySettingsEvent.NameChange(it)) })
            }
        },
        dismissButton = {
            TextButton(onClick = { modelView.onEvent(CategorySettingsEvent.CloseFormDialog) }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                modelView.onEvent(
                    CategorySettingsEvent.DialogFormSubmit(name = name)
                )
            }) {
                Text(text = confirmButtonTitle)
            }
        },
        onDismissRequest = { modelView.onEvent(CategorySettingsEvent.CloseFormDialog) })
}