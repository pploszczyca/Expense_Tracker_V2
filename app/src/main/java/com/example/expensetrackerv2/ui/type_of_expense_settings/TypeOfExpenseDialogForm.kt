package com.example.expensetrackerv2.ui.type_of_expense_settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.database.models.Type
import com.example.expensetrackerv2.database.models.TypeOfExpense

@Composable
fun TypeOfExpenseDialogForm(
    modelView: TypeOfExpenseSettingsModelView
) {
    val id by modelView.id
    val name by modelView.name
    val type by modelView.type

    val confirmButtonTitle =
        stringResource(id = if (modelView.isThisNewTypeOfExpense()) R.string.add else R.string.update)

    AlertDialog(
        title = { Text(text = stringResource(id = R.string.type_of_expense_form_title)) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    label = { Text(stringResource(id = R.string.name)) },
                    onValueChange = { modelView.onEvent(TypeOfExpenseSettingsEvent.NameChange(it)) })

                Row(
                    modifier = Modifier
                        .selectableGroup(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Type.values().forEach { typeValue ->
                        RadioButton(
                            selected = typeValue == type,
                            onClick = {
                                modelView.onEvent(
                                    TypeOfExpenseSettingsEvent.TypeChange(typeValue)
                                )
                            })
                        Text(text = typeValue.name, style = MaterialTheme.typography.subtitle1)
                    }
                }
            }
        },
        dismissButton = {
            TextButton(onClick = { modelView.onEvent(TypeOfExpenseSettingsEvent.CloseFormDialog()) }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                modelView.onEvent(
                    TypeOfExpenseSettingsEvent.DialogFormSubmit(
                        TypeOfExpense(
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
        onDismissRequest = { modelView.onEvent(TypeOfExpenseSettingsEvent.CloseFormDialog()) })
}