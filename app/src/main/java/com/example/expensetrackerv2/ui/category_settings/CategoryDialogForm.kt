package com.example.expensetrackerv2.ui.category_settings

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
import com.example.expensetrackerv2.R
import com.github.pploszczyca.expensetrackerv2.domain.Category

@Composable
fun CategoryDialogForm(
    modelView: CategorySettingsViewModel,
) {
    val id by modelView.id
    val name by modelView.name
    val type by modelView.categoryType

    val confirmButtonTitle =
        stringResource(id = if (modelView.isThisNewTypeOfExpense()) R.string.add else R.string.update)

    AlertDialog(
        title = { Text(text = stringResource(id = R.string.type_of_expense_form_title)) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    label = { Text(stringResource(id = R.string.name)) },
                    onValueChange = { modelView.onEvent(CategorySettingsEvent.NameChange(it)) })

                Row(
                    modifier = Modifier
                        .selectableGroup(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Category.Type.values().forEach { typeValue ->
                        RadioButton(
                            selected = typeValue == type,
                            onClick = {
                                modelView.onEvent(
                                    CategorySettingsEvent.TypeChange(typeValue)
                                )
                            })
                        Text(text = typeValue.name, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        },
        dismissButton = {
            TextButton(onClick = { modelView.onEvent(CategorySettingsEvent.CloseFormDialog()) }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                modelView.onEvent(
                    CategorySettingsEvent.DialogFormSubmit(
                        Category(
                            id = id,
                            name = name,
                            type = type
                        )
                    )
                )
            }) {
                Text(text = confirmButtonTitle)
            }
        },
        onDismissRequest = { modelView.onEvent(CategorySettingsEvent.CloseFormDialog()) })
}