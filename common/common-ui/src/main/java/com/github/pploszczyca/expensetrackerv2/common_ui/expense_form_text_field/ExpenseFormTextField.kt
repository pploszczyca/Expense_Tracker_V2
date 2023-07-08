package com.github.pploszczyca.expensetrackerv2.common_ui.expense_form_text_field

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp

@Composable
fun ExpenseFormTextField(
    value: Any,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit = {},
    icon: ImageVector,
    label: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Words),
    enabled: Boolean = true,
    readOnly: Boolean = false,
    colors: TextFieldColors = OutlinedTextFieldDefaults.colors(),
) {
    OutlinedTextField(
        value = value.toString(),
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = keyboardOptions,
        leadingIcon = {
            Icon(
                icon,
                contentDescription = null
            )
        },
        enabled = enabled,
        readOnly = readOnly,
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
        colors = colors,
    )
}
