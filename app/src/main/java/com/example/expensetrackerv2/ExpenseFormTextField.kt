package com.example.expensetrackerv2

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@Composable
fun ExpenseFormTextField(
    value: Any,
    onValueChange: (String) -> Unit,
    icon: ImageVector,
    label: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words)
) {
    OutlinedTextField(
        value = value.toString(),
        onValueChange = { onValueChange(it) },
        label = { Text(label) },
        keyboardOptions = keyboardOptions,
        leadingIcon = {
            Icon(
                icon,
                contentDescription = null
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    )
}