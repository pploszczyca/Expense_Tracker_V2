package com.example.expensetrackerv2.ui.type_of_expense_settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.expensetrackerv2.R
import com.example.expensetrackerv2.database.models.Type
import com.example.expensetrackerv2.database.models.TypeOfExpense

@Composable
fun TypeOfExpenseDialogForm(
    typeOfExpense: TypeOfExpense,
    confirmButtonTitle: String,
    onConfirmButtonClick: (TypeOfExpense) -> Unit,
    onDismissButtonClick: () -> Unit
) {
    var name by remember {
        mutableStateOf(typeOfExpense.name)
    }
    var type by remember {
        mutableStateOf(typeOfExpense.type)
    }

    AlertDialog(
        title = { Text(text = stringResource(id = R.string.type_of_expense_form_title)) },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    label = { Text(stringResource(id = R.string.name)) },
                    onValueChange = { name = it })

                Row(
                    modifier = Modifier
                        .selectableGroup(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Type.values().forEach { typeValue ->
                        RadioButton(selected = typeValue == type, onClick = { type = typeValue })
                        Text(text = typeValue.name, style = MaterialTheme.typography.subtitle1)
                    }
                }
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismissButtonClick() }) {
                Text(text = stringResource(id = R.string.cancel))
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirmButtonClick(
                    TypeOfExpense(
                        id = typeOfExpense.id,
                        name = name,
                        type = type
                    )
                )
            }) {
                Text(text = confirmButtonTitle)
            }
        },
        onDismissRequest = { onDismissButtonClick() })
}